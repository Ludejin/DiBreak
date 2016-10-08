package com.zero.dibreak.domain.dao;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * Created by Jin_ on 2016/9/25
 * 邮箱：Jin_Zboy@163.com
 */

public class BaseDao {
    Realm mRealm;

    BaseDao(Realm realm) {
        mRealm = realm;
    }

    public boolean insert(RealmObject object) {
        try {
            mRealm.beginTransaction();
            mRealm.insert(object);
            mRealm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            mRealm.cancelTransaction();
            return false;
        }
    }

    public boolean delete(Class<? extends RealmModel> clazz) {
        try {
            mRealm.beginTransaction();
            mRealm.delete(clazz);
            mRealm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            mRealm.cancelTransaction();
            return false;
        }
    }

    public void close() {
        if (!mRealm.isClosed()) {
            mRealm.close();
        }
    }
}
