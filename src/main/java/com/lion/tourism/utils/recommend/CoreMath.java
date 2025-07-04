package com.lion.tourism.utils.recommend;


import com.lion.tourism.utils.recommend.dto.RelateDTO;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 推荐算法
 */
public class CoreMath {

    /**
     * 推荐计算
     */

    public List<String> recommend(String userId, List<RelateDTO> list) {
        Map<Double, String> distances = computeNearestNeighbor(userId, list);
        if (distances.isEmpty()) {
            return Collections.emptyList();
        }

        String nearest = distances.entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElse(null);

        Map<String, List<RelateDTO>> userMap = list.stream()
                .collect(Collectors.groupingBy(RelateDTO::getUserId));

        List<String> neighborItemList = userMap.get(nearest).stream()
                .filter(e -> e.getIndex() >= 3)
                .map(RelateDTO::getProductId)
                .collect(Collectors.toList());

        List<String> userItemList = userMap.get(userId).stream()
                .map(RelateDTO::getProductId)
                .collect(Collectors.toList());

        return neighborItemList.stream()
                .filter(item -> !userItemList.contains(item))
                .sorted()
                .collect(Collectors.toList());
    }
    /**
     * 在给定userId的情况下，计算其他用户和它的相关系数并排序
     */
    private Map<Double, String> computeNearestNeighbor(String userId, List<RelateDTO> list) {
        //对同一个用户id数据，做分组
        Map<String, List<RelateDTO>> userMap = list.stream().collect(Collectors.groupingBy(RelateDTO::getUserId));
        //treemap是从小到大排好序的
        Map<Double, String> distances = new TreeMap<>();

        userMap.forEach((k, v) -> {
            if (!k.equals(userId)) {
                Double distance = pearsonDis(v, userMap.get(userId));
                if(distance != null){
                    //相关系数 ： 用户id
                    distances.put(distance, k);
                }
            }
        });
        return distances;
    }
//    private Map<Double, String> computeNearestNeighbor(String userId, List<RelateDTO> list) {
//        Map<String, List<RelateDTO>> userMap = list.stream()
//                .collect(Collectors.groupingBy(RelateDTO::getUserId));
//
//        Map<Double, String> distances = new TreeMap<>(Collections.reverseOrder()); // 从大到小排序
//
//        userMap.forEach((k, v) -> {
//            if (!k.equals(userId)) {
//                Double distance = pearsonDis(v, userMap.get(userId));
//                if (distance != null && distance != 0.0) { // 仅保留相关性非 0 的用户
//                    distances.put(distance, k);
//                }
//            }
//        });
//        return distances;
//    }


    /**
     * 计算两个序列间的相关系数
     *
     * @param xList 其他用户的数据集
     * @param yList 当前用户的数据集
     * @return 相关系数
     */
    private Double pearsonDis(List<RelateDTO> xList, List<RelateDTO> yList) {
        if (xList == null || xList.isEmpty() || yList == null || yList.isEmpty()) {
            return 0.0;
        }

        // 将 xList 转为 Map，仅保留评分 >= 3 的商品
        Map<String, Integer> xMap = xList.stream()
                .filter(x -> x.getIndex() >= 3) // 筛选评分 ≥ 3 的商品
                .collect(Collectors.toMap(RelateDTO::getProductId, RelateDTO::getIndex));

        // 定义两个列表，用于存储共同商品的评分
        List<Integer> xs = new ArrayList<>();
        List<Integer> ys = new ArrayList<>();

        // 遍历 yList，匹配共同商品
        yList.stream()
                .filter(y -> y.getIndex() >= 3) // 筛选评分 ≥ 3 的商品
                .forEach(y -> {
                    if (xMap.containsKey(y.getProductId())) {
                        xs.add(xMap.get(y.getProductId()));
                        ys.add(y.getIndex());
                    }
                });

        if (xs.isEmpty() || ys.isEmpty()) {
            return 0.0;
        }

        return getRelate(xs, ys);
    }


    /**
     * 方法描述: 皮尔森（pearson）相关系数计算
     * (x1,y1) 理解为 a 用户对 x 商品的评分和对 y 商品的评分
     *
     * @param xs 其他用户数据分布
     * @param ys 当前用户数据分布
     * @Return 相关系数值
     */
    public static Double getRelate(List<Integer> xs, List<Integer> ys) {
        int n = xs.size();
        double Ex = xs.stream().mapToDouble(x -> x).sum();
        double Ey = ys.stream().mapToDouble(y -> y).sum();
        double Ex2 = xs.stream().mapToDouble(x -> Math.pow(x, 2)).sum();
        double Ey2 = ys.stream().mapToDouble(y -> Math.pow(y, 2)).sum();
        double Exy = IntStream.range(0, n).mapToDouble(i -> xs.get(i) * ys.get(i)).sum();
        double numerator = Exy - Ex * Ey / n;
        double denominator = Math.sqrt((Ex2 - Math.pow(Ex, 2) / n) * (Ey2 - Math.pow(Ey, 2) / n));
        if (Double.isNaN(numerator) || denominator == 0) {
            return 0.0;
        }
        return -numerator / denominator;
    }


}
