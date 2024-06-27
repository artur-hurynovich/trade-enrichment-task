package com.verygoodbank.tes.validator;

import com.verygoodbank.tes.model.AbstractTradeData;

public interface TradeDataDateValidator {

    boolean isValidDate(AbstractTradeData tradeData);
}
