package name.valery1707.problem.leet.code;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * You are given an integer array {@code heights} representing the heights of buildings, some {@code bricks}, and some {@code ladders}.
 * <p>
 * You start your journey from building {@code 0} and move to the next building by possibly using bricks or ladders.
 * <p>
 * While moving from building {@code i} to building {@code i+1} (<b>0-indexed</b>),
 * <ul>
 * <li>If the current building's height is <b>greater than or equal</b> to the next building's height, you do <b>not</b> need a ladder or bricks.</li>
 * <li>If the current building's height is <b>less than</b> the next building's height, you can either use <b>one ladder</b> or {@code (h[i+1] - h[i])} <b>bricks</b>.</li>
 * </ul>
 * Return the furthest building index (0-indexed) you can reach if you use the given ladders and bricks optimally.
 * <p>
 * Constraints:
 * <ul>
 * <li>{@code 1 <= heights.length <= 10^5}</li>
 * <li>{@code 1 <= heights[i] <= 10^6}</li>
 * <li>{@code 0 <= bricks <= 10^9}</li>
 * <li>{@code 0 <= ladders <= heights.length}</li>
 * </ul>
 *
 * @see <a href="https://leetcode.com/problems/furthest-building-you-can-reach/">1642. Furthest Building You Can Reach</a>
 */
public interface FurthestBuildingYouCanReachJ {

    int furthestBuilding(int[] heights, int bricks, int ladders);

    enum Implementation implements FurthestBuildingYouCanReachJ {
        priorityQueue {
            @Override
            public int furthestBuilding(int[] heights, int bricks, int ladders) {
                var usedBricks = new PriorityQueue<Integer>(Comparator.reverseOrder());
                int maxIndex = heights.length - 1;
                for (int index = 0; index < maxIndex; index++) {
                    var diff = heights[index + 1] - heights[index];
                    if (diff <= 0) {
                        continue;
                    }
                    //Всегда используем кирпичи
                    usedBricks.offer(diff);
                    bricks -= diff;
                    //Если кирпичей не хватило
                    if (bricks < 0) {
                        //И есть лестницы
                        if (ladders > 0) {
                            //То считаем что на самый большой предыдущий пролёт мы использовали лестницу
                            ladders--;
                            bricks += usedBricks.poll();
                        } else {
                            //Лестниц нет - путешествие закончено
                            return index;
                        }
                    }
                }
                //Смогли дойти до конца
                return maxIndex;
            }
        },

        /**
         * Попытка оптимизировать по памяти:
         * <ul>
         * <li>{@code int[]} должен быть эффективнее по памяти по сравнению с {@code Object[]} внутри {@link PriorityQueue}</li>
         * <li>при использовании {@code int[]} не будет требоваться боксинг</li>
         * </ul>
         * <p>
         * На LeetCode умирает из-за превышения времени выполнения.
         */
        array {
            @Override
            public int furthestBuilding(int[] heights, int bricks, int ladders) {
                var usedBricksValues = new int[8];
                var usedBricksCount = 0;
                int maxIndex = heights.length - 1;
                for (int index = 0; index < maxIndex; index++) {
                    var diff = heights[index + 1] - heights[index];
                    if (diff <= 0) {
                        continue;
                    }
                    //Всегда используем кирпичи
                    if (usedBricksCount == usedBricksValues.length) {
                        usedBricksValues = Arrays.copyOf(usedBricksValues, usedBricksCount < 64 ? usedBricksCount * 2 : (int) (usedBricksCount * 1.5));
                        Arrays.sort(usedBricksValues);
                    }
                    //Заполняем с конца для учёта дальнейшей сортировки
                    usedBricksValues[usedBricksValues.length - 1 - usedBricksCount++] = diff;
                    bricks -= diff;
                    //Если кирпичей не хватило
                    if (bricks < 0) {
                        //И есть лестницы
                        if (ladders > 0) {
                            //То считаем что на самый большой предыдущий пролёт мы использовали лестницу
                            ladders--;
                            //Нули выходят в начало
                            Arrays.sort(usedBricksValues);
                            bricks += usedBricksValues[usedBricksValues.length - 1];
                            usedBricksCount--;
                            usedBricksValues[usedBricksValues.length - 1] = 0;
                            Arrays.sort(usedBricksValues);
                        } else {
                            //Лестниц нет - путешествие закончено
                            return index;
                        }
                    }
                }
                //Смогли дойти до конца
                return maxIndex;
            }
        },
    }

}
