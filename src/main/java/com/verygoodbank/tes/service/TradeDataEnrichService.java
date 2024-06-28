package com.verygoodbank.tes.service;

import java.util.List;

public interface TradeDataEnrichService {

    List<String[]> enrich(List<String[]> originalLines);
}
