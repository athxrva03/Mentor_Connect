package com.mentorconnect.service;

import com.mentorconnect.entity.Assignment;
import com.mentorconnect.entity.AssignmentStatus;
import com.mentorconnect.entity.User;
import com.mentorconnect.repository.AssignmentRepository;
import com.mentorconnect.repository.UserRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private UserRepository userRepository;

    private final String uploadDir = "uploads/";

    public Assignment uploadAssignment(Long userId, String title, MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        if (contentType == null || !(contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("application/pdf"))) {
            throw new RuntimeException("Only JPG, PNG, and PDF files are allowed");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Path copyLocation = Paths.get(uploadDir + file.getOriginalFilename());
        Files.createDirectories(copyLocation.getParent());
        Files.copy(file.getInputStream(), copyLocation, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        Assignment assignment = Assignment.builder()
                .user(user)
                .title(title)
                .filePath(copyLocation.toString())
                .status(AssignmentStatus.SUBMITTED)
                .build();

        return assignmentRepository.save(assignment);
    }

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public List<Assignment> getAssignmentsByUserId(Long userId) {
        return assignmentRepository.findByUserId(userId);
    }
    public Assignment verifyAssignment(Long id) {
        Assignment assignment = assignmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Assignment not found"));
        assignment.setStatus(AssignmentStatus.VERIFIED);
        return assignmentRepository.save(assignment);
    }

    public ByteArrayInputStream exportToExcel() throws IOException {
        List<Assignment> assignments = assignmentRepository.findAll();
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Assignments");

            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "User", "Title", "Date", "Status"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            int rowIdx = 1;
            for (Assignment assignment : assignments) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(assignment.getId());
                row.createCell(1).setCellValue(assignment.getUser().getName());
                row.createCell(2).setCellValue(assignment.getTitle());
                row.createCell(3).setCellValue(assignment.getSubmissionDate().toString());
                row.createCell(4).setCellValue(assignment.getStatus().toString());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    public ByteArrayInputStream exportToPdf() throws Exception {
        List<Assignment> assignments = assignmentRepository.findAll();
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();

        document.add(new Paragraph("Assignment Report"));
        document.add(new Paragraph(" "));

        for (Assignment assignment : assignments) {
            document.add(new Paragraph(
                String.format("ID: %d | User: %s | Title: %s | Status: %s",
                assignment.getId(), assignment.getUser().getName(), assignment.getTitle(), assignment.getStatus())
            ));
        }

        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

    public long getSubmittedCount() {
        return assignmentRepository.countByStatus(AssignmentStatus.SUBMITTED);
    }
}
