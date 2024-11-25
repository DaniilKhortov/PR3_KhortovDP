import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncCake {
    public static void main(String[] args) {

        System.out.println("Пекарня відкривається!");

        CompletableFuture<Void> LvivskaPekarna = CompletableFuture.runAsync(() -> {
            System.out.println("Шеф пекар Гайнс приходить на кухню й інспектує роботу пекарів!");
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("Шеф пекар Гайнс починає кричати на всіх пекарів!");
        });



        CompletableFuture<Void> thenApplyRudel = LvivskaPekarna.thenRunAsync(() -> {
            System.out.println("В паніці герр Рудель починає замішувати тісто.");
        });


        CompletableFuture<String> createfirstStep = CompletableFuture.supplyAsync(() -> {
            System.out.println("Герр Мігель недочекаючись тіста, викидує всі запаси ягод у форму!");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }

            return "полуничний";
        });

        CompletableFuture<String> finishfirstStep = createfirstStep.thenApplyAsync(result -> {
            System.out.println("Герр Вільгельм вириває у Руделя тісто, й заливає його в форму!");
            return result + " перший поверх, ";
        });

        CompletableFuture<String> createSecondStep = CompletableFuture.supplyAsync(() -> {
            System.out.println("Ображений Рудель, забирає з холодильника увесь шоколад!");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "шоколадний";
        });
        CompletableFuture<String> finishSecondStep = createSecondStep.thenApplyAsync(result2 -> {

            System.out.println("О ні! Герр Еріх бачить усе це неподобство, та вирубає Руделя  гольцкелем!");
            return result2 + " другий поверх";
        });

        CompletableFuture<String> completeCake = finishfirstStep.thenCombineAsync(finishSecondStep, (first, second) -> {
            System.out.println("Герр Ервін вривається, підковзується та складає обидва поверхи разом!");
            return "Торт, який має " + first + " і " + second;
        });


        CompletableFuture<Void> finalStep = completeCake.thenAcceptAsync(cake -> {
            System.out.println("Раптом відбувається диво! "+cake+" злітає, та приземляється прямо у голову герра Гайнса");
        });

        try {
            finalStep.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("Ось така дивина в Галичні, малята! Але той торт, все ще сниться мені в кошмарах...");
    }

    }



