package lta.com.audioRecord.data.db.dao;

import lta.com.audioRecord.data.db.model.RecordModel;

/**
 * @author: LuTaiAn
 * @className: RecordDao
 * @description:
 * @date: 2017/4/26
 */
public class RecordDao extends BaseDao<RecordModel> {
    protected static RecordDao mInstance;

    public RecordDao() {
        super();
    }

    public static synchronized RecordDao getInstance() {
        if(mInstance == null) {
            mInstance = new RecordDao();
        }
        return  mInstance;
    }
}
