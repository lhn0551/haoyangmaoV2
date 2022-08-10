package com.h.haoyangmaov2;

import android.content.Context;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SpUtils {

    private static SpUtils mInstance;
    private static MMKV mv;

    private SpUtils() {
        mv = MMKV.defaultMMKV();
    }

    /**
     * 初始化MMKV,只需要初始化一次，建议在Application中初始化
     */
    public static SpUtils getInstance() {
        if (mInstance == null) {
            synchronized (SpUtils.class) {
                if (mInstance == null) {
                    mInstance = new SpUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static void encode(String key, Object object) {
        if (object == null) {
            return;
        }
        if (object instanceof String) {
            mv.encode(key, (String) object);
        } else if (object instanceof Integer) {
            mv.encode(key, (Integer) object);
        } else if (object instanceof Boolean) {
            mv.encode(key, (Boolean) object);
        } else if (object instanceof Float) {
            mv.encode(key, (Float) object);
        } else if (object instanceof Long) {
            mv.encode(key, (Long) object);
        } else if (object instanceof Double) {
            mv.encode(key, (Double) object);
        } else if (object instanceof byte[]) {
            mv.encode(key, (byte[]) object);
        } else {
            mv.encode(key, object.toString());
        }
    }

    public static void encodeSet(String key, Set<String> sets) {
        mv.encode(key, sets);
    }

    public static void encodeParcelable(String key, Parcelable obj) {
        mv.encode(key, obj);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    public static Integer decodeInt(String key) {
        return mv.decodeInt(key, 0);
    }

    public static Integer decodeInt(String key, int defaultValue) {
        return mv.decodeInt(key, defaultValue);
    }

    public static Double decodeDouble(String key) {
        return mv.decodeDouble(key, 0.00);
    }

    public static Long decodeLong(String key) {
        return mv.decodeLong(key, 0L);
    }

    public static Boolean decodeBoolean(String key) {
        return mv.decodeBool(key, false);
    }

    public static Boolean decodeBoolean(String key, boolean flag) {
        return mv.decodeBool(key, flag);
    }

    public static Float decodeFloat(String key) {
        return mv.decodeFloat(key, 0F);
    }

    public static byte[] decodeBytes(String key) {
        return mv.decodeBytes(key);
    }

    public static String decodeString(String key) {
        return mv.decodeString(key, "");
    }

    public static String decodeString(String key, String defaultValue) {
        return mv.decodeString(key, defaultValue);
    }

    public static Set<String> decodeStringSet(String key) {
        return mv.decodeStringSet(key, Collections.emptySet());
    }

    public static Parcelable decodeParcelable(String key, Class tClass) {
        return mv.decodeParcelable(key, tClass);
    }


    public static <T> Boolean setArray(Context mContext, List<T> list, String name) {
        if (list == null || list.size() == 0) { //清空
            mv.putInt(name + "size", 0);
            int size = mv.getInt(name + "size", 0);
            for (int i = 0; i < size; i++) {
                if (mv.getString(name + i, null) != null) {
                    mv.remove(name + i);
                }
            }
        } else {
            mv.putInt(name + "size", list.size());
            for (int i = 0; i < list.size(); i++) {
                mv.remove(name + i);
                mv.remove(new Gson().toJson(list.get(i)));//删除重复数据 先删后加
                mv.putString(name + i, new Gson().toJson(list.get(i)));
            }
        }
        return mv.commit();
    }

    public static <T> ArrayList<T> getArray(Context mContext, String name, T bean) {
        ArrayList<T> list = new ArrayList<>();
        int size = mv.getInt(name + "size", 0);
        for (int i = 0; i < size; i++) {
            if (mv.getString(name + i, null) != null) {
                try {
                    list.add((T) new Gson().fromJson(mv.getString(name + i, null), bean.getClass()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return list;
    }

    /**
     * 移除某个key对
     *
     * @param key
     */
    public static void removeKey(String key) {
        mv.removeValueForKey(key);
    }

    /**
     * 清除所有key
     */
    public static void clearAll() {
        mv.clearAll();
    }
}
