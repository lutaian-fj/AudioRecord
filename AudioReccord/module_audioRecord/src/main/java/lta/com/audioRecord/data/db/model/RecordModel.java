package lta.com.audioRecord.data.db.model;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author: LuTaiAn
 * @className: RecordModel
 * @description: 录音记录的Model
 * @date: 2017/4/26
 */
@DatabaseTable(tableName = "t_record")
public class RecordModel extends BaseModel {
    @DatabaseField(columnName = "record_name", useGetSet = true)
    @Expose
    private String recordName; //录音文件名
    @DatabaseField(columnName = "record_length", useGetSet = true)
    @Expose
    private long recordLength; //录音长度

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public long getRecordLength() {
        return recordLength;
    }

    public void setRecordLength(long recordLength) {
        this.recordLength = recordLength;
    }
}
