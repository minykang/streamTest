package step02;
import java.util.*;
import java.util.stream.*;
public class ParallelEfficiencyTest {
    public static void main(String[] args) {
        // 테스트 데이터: 1부터 1000,000까지의 숫자
        int limit = 1000_000;
        List<Integer> numbers = IntStream.rangeClosed(1, limit).boxed().collect(Collectors.toList());
        System.out.println("테스트 데이터 범위: 1 ~ " + limit);
        System.out.println("작업 내용: 각 숫자가 소수인지 판별 (CPU 집중 작업)");
        System.out.println("=============================================");
        // 1. 순차 스트림(Sequential Stream) 측정
        long start = System.currentTimeMillis();
        long seqCount = numbers.stream()
                               .filter(ParallelEfficiencyTest::isPrime) // 무거운 연산
                               .count();
        long seqTime = System.currentTimeMillis() - start;
        System.out.println("순차 스트림 소요 시간: " + seqTime + " ms (찾은 소수 개수: " + seqCount + ")");
        // 2. 병렬 스트림(Parallel Stream) 측정 (Solution 방식)
        start = System.currentTimeMillis();
        long paraCount = numbers.parallelStream()
                                .filter(ParallelEfficiencyTest::isPrime) // 무거운 연산
                                .count();
        long paraTime = System.currentTimeMillis() - start;
        System.out.println("병렬 스트림 소요 시간: " + paraTime + " ms (찾은 소수 개수: " + paraCount + ")");
        // 3. 성능 개선율 계산
        double improvement = (double) (seqTime - paraTime) / seqTime * 100;
        System.out.println("=============================================");
        System.out.printf("병렬 처리로 인한 성능 개선율: %.2f%%\n", improvement);
    }
    // 소수 판별 로직 (의도적으로 복잡한 계산을 수행하도록 함)
    private static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}