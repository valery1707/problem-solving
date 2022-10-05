package name.valery1707.problem.leet.code;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Stream;

/**
 * There are {@code n} different online courses numbered from {@code 1} to {@code n}.
 * You are given an array {@code courses} where {@code courses[i] = [duration[i], lastDay[i]]} indicate that the {@code i-th} course should be taken
 * <b>continuously</b> for {@code duration[i]} days and must be finished before or on {@code lastDay[i]}.
 * <p>
 * You will start on the {@code 1-st} day and you cannot take two or more courses simultaneously.
 * <p>
 * Return the <i>maximum number of courses that you can take</i>.
 * <p>
 * Constraints:
 * <ul>
 * <li>{@code 1 <= courses.length <= 1^04}</li>
 * <li>{@code 1 <= duration[i], lastDay[i] <= 10^4}</li>
 * </ul>
 *
 * @see <a href="https://leetcode.com/problems/course-schedule-iii/">630. Course Schedule III</a>
 */
public interface CourseSchedule3J {

    int scheduleCourse(int[][] courses);

    enum Implementation implements CourseSchedule3J {
        streamSortReduce{
            @Override
            public int scheduleCourse(int[][] courses) {
                var entries = Stream.of(courses)
                    .map(it -> Map.entry(it[0], it[1]))
                    .sorted(Map.Entry.comparingByValue())
                    .toList();
                var result = entries.stream().reduce(
                    //courseCount to todayDay
                    Map.entry(0, 0),
                    (today, course) -> {
                        if (today.getValue() + course.getKey() <= course.getValue()) {
                            return Map.entry(today.getKey() + 1, today.getValue() + course.getKey());
                        } else {
                            return today;
                        }
                    }
                );
                return result.getKey();
            }
        },

        /**
         * @see <a href="https://leetcode.com/problems/course-schedule-iii/discuss/2185367/Java-or-Greedy-or-Explained">Discuss</a>
         */
        priorityQueue{
            @Override
            public int scheduleCourse(int[][] courses) {
                Arrays.sort(courses, Comparator.comparingInt(it -> it[1]));
                var heap = new PriorityQueue<Integer>(courses.length, Comparator.reverseOrder());
                for (int i = 0, today = 0; i < courses.length; i++) {
                    int duration = courses[i][0], lastDay = courses[i][1];
                    heap.add(duration);
                    if ((today += duration) > lastDay) {
                        today -= heap.poll();
                    }
                }
                return heap.size();
            }
        },
    }
}
