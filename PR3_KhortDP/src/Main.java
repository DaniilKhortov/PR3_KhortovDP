import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) {
        System.out.println("Програма почалася!");

        // Використання runAsync() для виконання задачі без повернення результату
        CompletableFuture<Void> runAsyncExample = CompletableFuture.runAsync(() -> {
            System.out.println("Запуск runAsync(): Виконується у фоновому потоці.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("Завершено runAsync().");
        });

        // Використання supplyAsync() для виконання задачі з поверненням результату
        CompletableFuture<String> supplyAsyncExample = CompletableFuture.supplyAsync(() -> {
            System.out.println("Запуск supplyAsync(): Обробка даних.");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Результат з supplyAsync.";
        });

        // Використання thenApplyAsync() для трансформації результату
        CompletableFuture<String> thenApplyExample = supplyAsyncExample.thenApplyAsync(result -> {
            System.out.println("Запуск thenApplyAsync(): Обробка результату.");
            return result + " Додано обробку.";
        });

        // Використання thenAcceptAsync() для виконання дії з результатом
        CompletableFuture<Void> thenAcceptExample = thenApplyExample.thenAcceptAsync(result -> {
            System.out.println("Запуск thenAcceptAsync(): Прийом результату.");
            System.out.println("Отримано: " + result);
        });

        // Використання thenRunAsync() для виконання дії без доступу до результату
        CompletableFuture<Void> thenRunExample = thenAcceptExample.thenRunAsync(() -> {
            System.out.println("Запуск thenRunAsync(): Виконання фінальної дії.");
        });

        // Очікування завершення усіх задач
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(runAsyncExample, thenRunExample);
        try {
            allTasks.get(); // Очікуємо завершення усіх задач
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("Програма завершилася!");
    }
}