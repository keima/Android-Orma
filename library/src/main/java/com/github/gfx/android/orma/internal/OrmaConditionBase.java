package com.github.gfx.android.orma.internal;

import com.github.gfx.android.orma.OrmaConnection;
import com.github.gfx.android.orma.Schema;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class OrmaConditionBase<T, C extends OrmaConditionBase> {

    protected final OrmaConnection connection;

    protected final Schema<T> schema;

    protected String whereConjunction = " AND ";

    @Nullable
    protected StringBuilder whereClause;

    @Nullable
    protected List<String> whereArgs;

    public OrmaConditionBase(OrmaConnection connection, Schema<T> schema) {
        this.connection = connection;
        this.schema = schema;
    }

    @SuppressWarnings("unchecked")
    public C where(@NonNull String clause, @NonNull Object... args) {
        if (whereClause == null) {
            whereClause = new StringBuilder(clause.length() + 2);
            whereArgs = new ArrayList<>(args.length);
        } else {
            whereClause.append(whereConjunction);
        }

        whereClause.append('(');
        whereClause.append(clause);
        whereClause.append(')');

        for (Object arg : args) {
            if (arg == null) {
                whereArgs.add(null);
            } else {
                whereArgs.add(arg.toString());
            }
        }
        return (C) this;
    }

    @SuppressWarnings("unchecked")
    public C and() {
        whereConjunction = " AND ";
        return (C) this;
    }

    @SuppressWarnings("unchecked")
    public C or() {
        whereConjunction = " OR ";
        return (C) this;
    }

    @Nullable
    protected String getWhereClause() {
        return whereClause != null ? whereClause.toString() : null;
    }

    @Nullable
    protected String[] getWhereArgs() {
        if (whereArgs != null) {
            String[] array = new String[whereArgs.size()];
            return whereArgs.toArray(array);
        } else {
            return null;
        }
    }
}
