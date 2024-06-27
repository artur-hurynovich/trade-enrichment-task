package com.verygoodbank.tes.service;

import com.verygoodbank.tes.model.impl.IdentifiedTradeData;
import com.verygoodbank.tes.model.impl.NamedTradeData;

public interface TradeDataMapper {

    NamedTradeData map(IdentifiedTradeData identifiedTradeData);
}
