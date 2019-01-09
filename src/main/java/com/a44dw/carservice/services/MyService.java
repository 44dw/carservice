package com.a44dw.carservice.services;

import com.a44dw.carservice.entities.*;

public abstract class MyService {
    abstract void doDatabaseEntityOperation(DatabaseEntity databaseEntity);
    public abstract void deleteDatabaseEntity(DatabaseEntity databaseEntity);
}
