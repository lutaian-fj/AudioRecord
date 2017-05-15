package lta.com.audioRecord.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: LuTaiAn
 * @className: FileUtil
 * @description: File工具类
 * @date: 2017/5/10
 */
public final class FileUtil {

    private static final String TAG = "FileUtil";

    private FileUtil() {
    }

    /**
     * 判断SD卡是否存在 <br>
     * Created 2014-8-22 下午5:42:24
     *
     * @return SD卡存在返回true，否则返回false
     * @author huangyx
     */
    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 返回SD卡跟目录 <br>
     * Created 2014-8-22 下午5:41:22
     *
     * @return SD卡跟目录
     * @author huangyx
     */
    public static String getSdCardPath() {
        File sdDir;
        boolean sdCardExist = isSdCardExist(); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
            return sdDir.toString();
        } else {
            return null;
        }
    }


    public static final String getFileSuffix(String fileName) {
        int idx = fileName.lastIndexOf(".");
        return fileName.substring(idx, fileName.length());
    }


    public static final String getMainFileName(String fileName) {
        int idx = fileName.lastIndexOf(".");
        return fileName.substring(0, idx);
    }

    public static final boolean isFileExists(String filePath) {
        try {
            File file = new File(filePath);
            return file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 文件复制类
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @return boolean
     */
    public static boolean copy(String srcFile, String destFile) {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] bytes = new byte[1024];
            int c;
            while ((c = in.read(bytes)) != -1) {
                out.write(bytes, 0, c);
            }
            out.flush();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Copy exception-->", e);
            return false;
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    deleteFile(files[i].getAbsolutePath()); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        } else {
            Log.d(TAG, "file no exists");
        }
    }


    /**
     * 删除path 下全部文件
     *
     * @param path
     * @return true删除成功
     */
    public static boolean delAllFile(String path) {
        return delAllFile(path, null);
    }

    /**
     * 删除文件夹内所有文件
     *
     * @param path
     * @param filenameFilter 过滤器 支持null
     * @return boolean
     */
    public static boolean delAllFile(String path, FilenameFilter filenameFilter) {
        boolean flag = false;

        File file = new File(path);
        if (!file.exists()) return true;
        if (!file.isDirectory()) return false;

        File[] tempList = file.listFiles(filenameFilter);
        int length = tempList.length;
        for (int i = 0; i < length; i++) {

            if (tempList[i].isFile()) {
                tempList[i].delete();
            }

            if (tempList[i].isDirectory()) {
                /**
                 * 删除内部文件
                 */
                delAllFile(tempList[i].getAbsolutePath(), filenameFilter);
                /**
                 * 删除空文件夹
                 */
                String[] ifEmptyDir = tempList[i].list();
                if (ifEmptyDir == null || ifEmptyDir.length <= 0) {
                    tempList[i].delete();
                }
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 删除文件夹
     *
     * @param folderPath
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容

            File f = new File(folderPath);
            f.delete(); // 删除空文件夹

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * @param fileName
     * @Title: getPathByFileName
     * @Description: 根据文件名返回文件路径
     * @param: file
     * @param: type 文件类型
     * @param fileName 文件名
     * @return:
     * @throws:
     */
    public static List<String> getPathByFileName(File file, String type, String fileName) {
        List<String> result = new ArrayList<>();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    File tmp = files[i];
                    if (tmp.isFile()) {
                        String name = tmp.getName();
                            name = name.substring(name.lastIndexOf("/") + 1);
                            if (name.equals(fileName + "." + type)) {
                                result.add(tmp.getAbsolutePath());
                            }

                    } else {
                        result.addAll(getPathByFileName(tmp, type, fileName));
                    }
                }
            }
        } else {
            if (file.isFile()) {
                String name = file.getName();
                    name = name.substring(name.lastIndexOf("/") + 1);
                    if (name.equals(fileName + "." + type)) {
                        result.add(file.getAbsolutePath());
                    }
            }
        }
        return result;
    }


}
