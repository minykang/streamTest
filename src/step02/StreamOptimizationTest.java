package step02;

import java.util.List;
import java.util.stream.IntStream;

public class StreamOptimizationTest {

    public static void main(String[] args) {
        // 대량 데이터 생성
        System.out.println("데이터 생성 중...");
        List<String> largeNames = IntStream.range(0, 1_000_000)
                .mapToObj(i -> "Alice" + i)
                .toList();
        System.out.println("데이터 생성 완료\n");

        // 메모리 올리는 웜업 연산
        for (int i = 0; i < 3; i++) {
            largeNames.stream().limit(10).toList();
        }

        // --- 최적화된 연산자 사용 (Optimized) ---
        long startOptimized = System.nanoTime();
        
        List<String> resultOptimized = largeNames.stream()
                .filter(name -> name.startsWith("A") && name.length() > 3)
                .map(name -> name.toUpperCase() + " is a name")
                .toList();
        
        long endOptimized = System.nanoTime();
        long durationOptimized = endOptimized - startOptimized;


        // --- 과한 중간 연산자 사용 (Original) ---
        long startOriginal = System.nanoTime();
        
        List<String> resultOriginal = largeNames.stream()
                .filter(name -> name.startsWith("A"))     
                .filter(name -> name.length() > 3)        
                .map(String::toUpperCase)                 
                .map(name -> name + " is a name")     
                .toList();            
        
        long endOriginal = System.nanoTime();
        long durationOriginal = endOriginal - startOriginal;

        // 결과 출력
        System.out.println("========================================");
        System.out.println("[1] Optimized - 먼저 실행됨");
        System.out.println("소요 시간: " + durationOptimized + " ns (" + (durationOptimized / 1_000_000.0) + " ms)");
        
        System.out.println("\n[2] Original - 나중에 실행됨");
        System.out.println("소요 시간: " + durationOriginal + " ns (" + (durationOriginal / 1_000_000.0) + " ms)");
        
        System.out.println("========================================");
        System.out.println("차이 (Original - Optimized): " + (durationOriginal - durationOptimized) + " ns");
        System.out.println("========================================");
    }
}