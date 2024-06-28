package com.verygoodbank.tes.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TradeDataCsvReader {

    List<String[]> read(MultipartFile file);
}
