package ink.lch.config.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TreeUtils {

    // 是否开启数据统计
    private static boolean needStatistics = false;
    // 扩展功能
    private static boolean other = false;
    // 待统计数据字段名称
    private static String statisticsFieldName = "";

    private static final String ID_FIELD_NAME = "id";
    private static final String PARENT_ID = "parentId";
    private static final String CHILD_LIST = "childList";

    /**
     * 封装树形结构
     * @param originalList 待处理原始数据集合
     * @param idFieldName 字段唯一标识
     * @param pidFieldName 用于区分父子级关系的字段名称
     * @param childListName 实体类中子级集合的名称
     * @return 树结构数据
     */
    public static <T> List<T> buildTree(List<T> originalList, String idFieldName, String pidFieldName, String childListName) {
        // 非空校验
        if (idFieldName == null || idFieldName.isEmpty()) {
            idFieldName = TreeUtils.ID_FIELD_NAME;
        }
        if (pidFieldName == null || pidFieldName.isEmpty()) {
            pidFieldName = TreeUtils.PARENT_ID;
        }
        if (childListName == null || childListName.isEmpty()) {
            childListName = TreeUtils.CHILD_LIST;
        }

        // 获取根节点(顶级节点)
        List<T> rootNodeList = new ArrayList<>();
        for (T t : originalList) {
            try {
                String parentId = BeanUtils.getProperty(t, pidFieldName);
                if (parentId.equals("0")) {
                    rootNodeList.add(t);
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                ex.printStackTrace();
            }
        }
        // 将根节点从原始list移除，减少下次处理数据(如果数据量过大,不建议使用removeAll,效率会非常的差,但是一般树形结构的数据量不会太多,如果过多前端的渲染也会非常的慢)
        originalList.removeAll(rootNodeList);
        // 递归封装树
        try {
            packTree(originalList, rootNodeList, idFieldName, pidFieldName, childListName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootNodeList;
    }

    /**
     * 扩展功能,有些树的构建需要伴随数据统计
     * @param statisticsFieldName 待统计数据字段名称
     * @param needStatistics 是否需要对数据进行构建
     * @param other 预留字段
     */
    public static void statistics(String statisticsFieldName, boolean needStatistics, boolean other) {
        TreeUtils.statisticsFieldName = statisticsFieldName;
        TreeUtils.needStatistics = needStatistics;
        TreeUtils.other = other;
    }

    /**
     * 递归封装每个上级类别的子级类别
     * @param originalList 待处理原始数据集合
     * @param rootNodeList 根节点(顶级节点)
     * @param idFieldName 字段唯一标识
     * @param pidFieldName 用于区分父子级关系的字段名称
     * @param childListName 实体类中子级集合的名称
     */
    private static <T> void packTree(List<T> originalList, List<T> rootNodeList, String idFieldName, String pidFieldName, String childListName) throws Exception {
        // 循环获取上级类别对应的子级
        for (T parentNode : rootNodeList) {
            // 找到当前父节点的子节点列表
            List<T> children = packChildren(parentNode, originalList, idFieldName, pidFieldName, childListName);
            if (children.isEmpty()) {
                continue;
            }
            // 将当前父节点的子节点从原始list移除，减少下次处理数据
            originalList.removeAll(children);
            // 开始下次递归
            packTree(originalList, children, idFieldName, pidFieldName, childListName);
        }
    }

    /**
     * 封装该父级下的所有子级
     * @param parentNode 父级
     * @param originalList 待处理原始数据集合
     * @param idFieldName 字段唯一标识
     * @param pidFieldName 用于区分父子级关系的字段
     * @return 该父级下的所有子级
     */
    private static <T> List<T> packChildren(T parentNode, List<T> originalList, String idFieldName, String pidFieldName, String childListName) throws Exception {
        String parentId = BeanUtils.getProperty(parentNode, idFieldName);
        // 过滤出当前父节点下的子节点集合
        List<T> childNodeList = originalList.stream().filter(e -> {
            try {
                return Objects.equals(parentId, BeanUtils.getProperty(e, pidFieldName));
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                ex.printStackTrace();
            }
            return false;
        }).collect(Collectors.toList());

        // do something
        if (needStatistics) {
            // 如果需要进行数据统计,可以在这里进行
        }

        if (!childNodeList.isEmpty()) {
            // 将子节点数据写入父节点中
            FieldUtils.writeDeclaredField(parentNode, childListName, childNodeList, true);
        }
        return childNodeList;
    }
}
