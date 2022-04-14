package _04_builder;

public class App {
    public static void main(String[] args) {
        TourDirector director = new TourDirector(new DefulatTourBuilder());
        TourPlan tourPlan = director.cancunTrip();
        TourPlan tourPlan1 = director.longBeachTrip();

        System.out.println(tourPlan.getTitle());
    }
}
