package com.verygoodbank.tes.service;

import java.util.List;

public interface TradeDataCsvWriter {

    byte[] write(List<String[]> lines);
}
