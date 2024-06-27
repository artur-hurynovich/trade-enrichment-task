package com.verygoodbank.tes.dao;

import java.util.Optional;

public interface ProductNameDao {

    Optional<String> getByProductId(String productId);
}
