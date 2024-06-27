package com.verygoodbank.tes.service;

import com.verygoodbank.tes.model.impl.IdentifiedTradeData;
import com.verygoodbank.tes.model.impl.NamedTradeData;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TradeDataCsvFileService {

    List<IdentifiedTradeData> parse(MultipartFile file);

    Resource write(List<NamedTradeData> namedTradeDataList);
}
