package top.yyyin.boot.exception.util;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertUtil {
    /* ---------- 单对象 ---------- */
    public static <S, T> T convert(S source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("转换失败", e);
        }
    }

    /* ---------- 列表 ---------- */
    public static <S, T> List<T> convertList(List<S> sourceList, Class<T> targetClass) {
        if (sourceList == null) {
            return null;
        }
        return sourceList.stream()
                .map(s -> convert(s, targetClass))
                .collect(Collectors.toList());
    }

    /* ---------- 忽略指定字段 ---------- */
    public static <S, T> T convertIgnore(S source, Class<T> targetClass, String... ignoreProperties) {
        if (source == null) {
            return null;
        }
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target, ignoreProperties);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("转换失败", e);
        }
    }
}
