package com.vlad.service;

import com.vlad.model.CSVDocument;
import com.vlad.model.IDocument;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.validator.routines.DateValidator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Service
public class DocumentService {

    public List<IDocument> getDocuments() throws IOException {
        String[] extensions = new String[] {"csv"};
        File folderCSV = new File(getFolderVariable());
        List<File> listOfFiles = (List<File>) FileUtils.listFiles(folderCSV, extensions, true);
        List<IDocument> documents = new ArrayList<>();


        for (File file : listOfFiles) {
            if (file.isFile() ) {
                CSVDocument document = new CSVDocument();
                document.setTitle(file.getName());
                document.setDate(new Date(file.lastModified()));
                Path currentPath = Paths.get(file.getAbsolutePath());
                FileOwnerAttributeView ownerAttributeView = Files.getFileAttributeView(currentPath, FileOwnerAttributeView.class);
                UserPrincipal owner = ownerAttributeView.getOwner();
                document.setUploader(owner.toString());
                document.setPathToFile(currentPath.toString());
                documents.add(document);
            }
        }
        return documents;
    }

    public Map<String, Integer> getHeader(String filePath) throws IOException {
        CSVParser parser = new CSVParser(new FileReader(getAbsolutePath(filePath)),
                CSVFormat.DEFAULT.withHeader());
        Map<String, Integer> header = parser.getHeaderMap();
        parser.close();
        return header;
    }

    public int getRows(String filePath) throws IOException {
        CSVParser parser = new CSVParser(new FileReader(getAbsolutePath(filePath)),
                CSVFormat.DEFAULT.withHeader());
        int lines = parser.getRecords().size();
        parser.close();
        return lines;
    }

    public int[] getColumns(String filePath) throws IOException {
        CSVParser parser = new CSVParser(new FileReader(getAbsolutePath(filePath)),
                CSVFormat.DEFAULT.withHeader());
        int columns = parser.getHeaderMap().size();
        int[] nullVals = new int[columns];
        for (CSVRecord record : parser) {
            for (int i = 0; i < columns; i++) {
                if (record.get(i).isEmpty()) {
                    nullVals[i]++;
                }
            }
        }

        parser.close();
        return nullVals;
    }

    public List<String> suggestColumnTypes(String filePath) throws IOException {
        CSVParser parser = new CSVParser(new FileReader(getAbsolutePath(filePath)),
                CSVFormat.DEFAULT.withHeader());
        int columns = parser.getHeaderMap().size();
        List<String> suggestedSchema = new ArrayList<>();
        List<CSVRecord> records = parser.getRecords();
        int notNullRows = 0;
        int numberOfRows = 9;
        List<CSVRecord> notNullRecords = new ArrayList<>();

        for (CSVRecord record: records) {
            boolean isNull = false;
            for (int i = 0; i < columns; i++) {
                if (record.get(i).isEmpty()) {
                    isNull = true;
                }
            }
            if (!isNull && notNullRows < numberOfRows) {
                notNullRecords.add(record);
                notNullRows++;
            } if (notNullRows == numberOfRows) {
                break;
            }
        }

        if (notNullRows < numberOfRows) {
            numberOfRows = notNullRows;
        }

        Map<String, Integer> columnsMap = parser.getHeaderMap();

        String[][] values = new String[columns][numberOfRows];

        notNullRows = 0;
        for (CSVRecord record : notNullRecords) {
            for (Map.Entry<String, Integer> entry : columnsMap.entrySet()) {
                values[entry.getValue()][notNullRows] = parseValue(record.get(entry.getValue()));
            }
            notNullRows++;
        }

        for (int i = 0; i < columns; i++) {
            Map<String,Long> valueCounts =
                    Arrays.asList(values[i]).stream()
                            .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));

            String dataType=valueCounts.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
            suggestedSchema.add(dataType);
        }

        return suggestedSchema;
    }

    private String parseValue(String value) {

        if (NumberUtils.isCreatable(value)) {
            if (value.contains(".") || value.contains("E")) {
                return "FLOAT";
            } else {
                return "INT";
            }
        } else if (Boolean.parseBoolean(value)) {
            return "BOOLEAN";
        } else if (isDate(value)) {
            return "DATE";
        } else {
            return "STRING";
        }
    }

    private boolean isDate(String value) {
        DateValidator validator = new DateValidator(false, -1);
        List<String> dateFormat = new ArrayList<>();

        dateFormat.add("dd-MM-yy");
        dateFormat.add("dd-MM-yyyy");
        dateFormat.add("MM-dd-yyyy");
        dateFormat.add("yyyy-MM-dd");
        dateFormat.add("yyyy-MM-dd HH:mm:ss");
        dateFormat.add("yyyy-MM-dd HH:mm:ss.SSS");
        dateFormat.add("yyyy-MM-dd HH:mm:ss.SSSZ");
        dateFormat.add("EEEEE MMMMM yyyy HH:mm:ss.SSSZ");
        dateFormat.add("dd/MM/yy");
        dateFormat.add("dd/MM/yyyy");
        dateFormat.add("MM/dd/yyyy");
        dateFormat.add("yyyy/MM/dd");
        dateFormat.add("yyyy/MM/dd HH:mm:ss");
        dateFormat.add("yyyy/MM/dd HH:mm:ss.SSS");
        dateFormat.add("yyyy/MM/dd HH:mm:ss.SSSZ");

        for (String datePattern : dateFormat) {
            if (validator.isValid(value, datePattern)) {
                return true;
            }
        }
        return false;
    }

    private static String getFolderVariable() {
        return System.getenv("CSV_REPO");
    }
    private String getAbsolutePath(String filePath) {
        return getFolderVariable() + "/" + filePath;
    }
}


