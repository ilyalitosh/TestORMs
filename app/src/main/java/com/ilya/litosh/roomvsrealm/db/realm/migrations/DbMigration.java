package com.ilya.litosh.roomvsrealm.db.realm.migrations;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class DbMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
        if(oldVersion == 0){
            schema.create("Car")
                    .addField("color", String.class)
                    .addField("fuelCapacity", Integer.class)
                    .addField("price", Integer.class);
            oldVersion++;
        }
    }

    @Override
    public int hashCode() {
        return 1 + super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof DbMigration);
    }
}
