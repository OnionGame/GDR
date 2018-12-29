package pl.gd;

import com.google.common.base.Stopwatch;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Einstein {

    public static void main(String a[]) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        List<List<Integer>> permutacjeDomow = new PermutateList<Integer>().permute(Arrays.asList(2, 3, 4, 5));
        List<List<Kolor>> permutacjeKolorow = new PermutateList<Kolor>().permute(Kolor.rest());
        List<List<Napoj>> permutacjeNapojow = new PermutateList<Napoj>().permute(Napoj.rest());
        List<List<Papierosy>> permutacjePapierosow = new PermutateList<Papierosy>().permute(Papierosy.rest());
        List<List<Zwierze>> permutacjeZwierza = new PermutateList<Zwierze>().permute(Zwierze.rest());


        for (List<Integer> permutacjaDomow : permutacjeDomow) {
            for (List<Kolor> permutacjaKolorow : permutacjeKolorow) {
                for (List<Napoj> permutacjaNapoow : permutacjeNapojow) {
                    for (List<Papierosy> permutacjaPapierosow : permutacjePapierosow) {
                        for (List<Zwierze> permutacjaZwerza : permutacjeZwierza) {
                            Czlowiek[] mieszkancy = stworz();
                            ustawDomy(mieszkancy, permutacjaDomow);
                            ustawKolory(mieszkancy, permutacjaKolorow);
                            ustawNapoje(mieszkancy, permutacjaNapoow);
                            ustawPapierosy(mieszkancy, permutacjaPapierosow);
                            ustawZwierza(mieszkancy, permutacjaZwerza);
                            boolean isOk = Stream.of(mieszkancy).allMatch(Czlowiek::spelniaZasady);
                            if (isOk && zasada3(mieszkancy) && zasada5(mieszkancy) && zasada9(mieszkancy) && zasada12(mieszkancy) && zasada13(mieszkancy)) {
                                wypisz(mieszkancy);
                            }
                        }
                    }
                }
            }
        }
        System.out.println(stopwatch);

    }

    private static void ustawDomy(Czlowiek[] all, List<Integer> permutacja) {
        int i = 0;
        for (Czlowiek czlowiek : all) {
            if (czlowiek.getNrDomuField() == null) {
                czlowiek.setNrDomuField(permutacja.get(i));
                i++;
            }
        }
    }

    private static void ustawKolory(Czlowiek[] all, List<Kolor> permutacja) {
        int i = 0;
        for (Czlowiek czlowiek : all) {
            if (czlowiek.getKolorField() == null) {
                czlowiek.setKolorField(permutacja.get(i));
                i++;
            }
        }
    }

    private static void ustawNapoje(Czlowiek[] all, List<Napoj> permutacja) {
        int i = 0;
        for (Czlowiek czlowiek : all) {
            if (czlowiek.getNapojField() == null) {
                czlowiek.setNapojField(permutacja.get(i));
                i++;
            }
        }
    }

    private static void ustawPapierosy(Czlowiek[] all, List<Papierosy> permutacja) {
        int i = 0;
        for (Czlowiek czlowiek : all) {
            if (czlowiek.getPapierosyField() == null) {
                czlowiek.setPapierosyField(permutacja.get(i));
                i++;
            }
        }
    }

    private static void ustawZwierza(Czlowiek[] all, List<Zwierze> permutacja) {
        int i = 0;
        for (Czlowiek czlowiek : all) {
            if (czlowiek.getZwierzeField() == null) {
                czlowiek.setZwierzeField(permutacja.get(i));
                i++;
            }
        }
    }

    private static boolean zasada3(Czlowiek[] all) {
        int b = 0, z = 0;
        for (Czlowiek czlowiek : all) {

            if (czlowiek.getKolorField().equals(Kolor.zielony)) {
                z = czlowiek.getNrDomuField();
            }
            if (czlowiek.getKolorField().equals(Kolor.bialy)) {
                b = czlowiek.getNrDomuField();
            }
        }
        return z - b == -1;
    }

    private static boolean zasada5(Czlowiek[] all) {

        int koty = 0, light = 0;
        for (Czlowiek czlowiek : all) {

            if (czlowiek.getZwierzeField().equals(Zwierze.koty)) {
                koty = czlowiek.getNrDomuField();
            }
            if (czlowiek.getPapierosyField().equals(Papierosy.light)) {
                light = czlowiek.getNrDomuField();
            }
        }
        return Math.abs(light - koty) == 1;

    }

    private static boolean zasada9(Czlowiek[] all) {
        int woda = 0, light = 0;
        for (Czlowiek czlowiek : all) {

            if (czlowiek.getNapojField().equals(Napoj.woda)) {
                woda = czlowiek.getNrDomuField();
            }
            if (czlowiek.getPapierosyField().equals(Papierosy.light)) {
                light = czlowiek.getNrDomuField();
            }
        }
        return Math.abs(light - woda) == 1;

    }

    private static boolean zasada12(Czlowiek[] all) {
        int norweg = 0, niebieski = 0;
        for (Czlowiek czlowiek : all) {

            if (czlowiek.getNarodowoscField().equals(Narodowosc.norweg)) {
                norweg = czlowiek.getNrDomuField();
            }
            if (czlowiek.getKolorField().equals(Kolor.niebieski)) {
                niebieski = czlowiek.getNrDomuField();
            }
        }
        return Math.abs(niebieski - norweg) == 1;

    }

    private static boolean zasada13(Czlowiek[] all) {
        int konie = 0, zolty = 0;
        for (Czlowiek czlowiek : all) {

            if (czlowiek.getZwierzeField().equals(Zwierze.konie)) {
                konie = czlowiek.getNrDomuField();
            }
            if (czlowiek.getKolorField().equals(Kolor.zolty)) {
                zolty = czlowiek.getNrDomuField();
            }
        }
        return Math.abs(zolty - konie) == 1;

    }

    private static Czlowiek[] stworz() {
        Czlowiek niemiec = new Czlowiek(Narodowosc.niemiec);
        Czlowiek norweg = new Czlowiek(Narodowosc.norweg);
        Czlowiek anglik = new Czlowiek(Narodowosc.anglik);
        Czlowiek szwed = new Czlowiek(Narodowosc.szwed);
        Czlowiek dunczyk = new Czlowiek(Narodowosc.dunczyk);

        norweg.setNrDomuField(1);//1
        anglik.setKolorField(Kolor.czerwony);//2
        dunczyk.setNapojField(Napoj.herbata);//4
        niemiec.setPapierosyField(Papierosy.fajka);//7
        szwed.setZwierzeField(Zwierze.psy);//11

        return new Czlowiek[]{norweg, dunczyk, anglik, niemiec, szwed};
    }

    private static void wypisz(Czlowiek[] next) {
        System.out.println();
        System.out.println(Stream.of(next).sorted().map(Czlowiek::getNrDomuField).map(Object::toString).collect(Collectors.joining("    |   ")));
        System.out.println(Stream.of(next).sorted().map(Czlowiek::getNarodowoscField).map(Object::toString).collect(Collectors.joining(" | ")));
        System.out.println(Stream.of(next).sorted().map(Czlowiek::getKolorField).map(Object::toString).collect(Collectors.joining(" | ")));
        System.out.println(Stream.of(next).sorted().map(Czlowiek::getNapojField).map(Object::toString).collect(Collectors.joining("  |  ")));
        System.out.println(Stream.of(next).sorted().map(Czlowiek::getPapierosyField).map(Object::toString).collect(Collectors.joining(" | ")));
        System.out.println(Stream.of(next).sorted().map(Czlowiek::getZwierzeField).map(Object::toString).collect(Collectors.joining("  |  ")));
    }
}
