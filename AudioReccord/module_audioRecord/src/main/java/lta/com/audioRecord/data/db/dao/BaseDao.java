package lta.com.audioRecord.data.db.dao;

import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import lta.com.audioRecord.data.db.DBHelper;
import lta.com.audioRecord.data.db.model.BaseModel;
import lta.com.audioRecord.utils.AppContextUtils;

/**
 * @author: LuTaiAn
 * @className: BaseDao
 * @description:
 * @date: 2017/4/26
 */
public class BaseDao<T extends BaseModel> {
    protected DBHelper mHelper;
    protected RuntimeExceptionDao<T, String> mDao;

    protected BaseDao() {
        mHelper = DBHelper.getInstance(AppContextUtils.getContext());
        mDao = mHelper.getRuntimeExceptionDao(getTClass());
    }

    public int add(T model) {
        if (TextUtils.isEmpty(model.getId())) {
            model.setId(UUID.randomUUID().toString());
        }
        model.setCreateTime(System.currentTimeMillis());
        model.setUpdateTime(System.currentTimeMillis());
        return mDao.create(model);
    }

    public void createOrUpdate(T model) {
        mDao.createOrUpdate(model);
    }

    public int delete(T model) {
        return mDao.delete(model);
    }

    public int deleteAll() {
        return mDao.delete(queryAll());
    }

    public List<T> queryAll() {
        return query(true);
    }

    public List<T> query(boolean fileterDeleted, Pair<String, Object>... pairs) {
        QueryBuilder queryBuilder = mDao.queryBuilder();
        try {
            final Where where = queryBuilder.where();
            if (fileterDeleted) {
                where.eq("deleted", 0);
            }
            if (pairs != null && pairs.length > 0) {
                if (!fileterDeleted) {
                    where.eq(pairs[0].first, pairs[0].second);
                }
                int start = fileterDeleted ? 0 : 1;

                for (int i = start; i < pairs.length; i++) {
                    where.and().eq(pairs[i].first, pairs[i].second);
                }
            }
            return queryBuilder.query();
        } catch (SQLException e) {
            Log.e("BaseDao", "BaseDao query ", e);
        }
        return null;
    }

    private Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

}
