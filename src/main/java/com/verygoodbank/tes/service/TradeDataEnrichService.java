package com.verygoodbank.tes.service;

import com.verygoodbank.tes.model.impl.IdentifiedTradeData;
import com.verygoodbank.tes.model.impl.NamedTradeData;

import java.util.List;

public interface TradeDataEnrichService {

    List<NamedTradeData> enrich(List<IdentifiedTradeData> identifiedTradeDataList);
}
