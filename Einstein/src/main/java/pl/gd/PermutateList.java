package pl.gd;

import java.util.ArrayList;
import java.util.List;

public class PermutateList<E> {

    public List<List<E>> permute(List<E> arr) {
        List<List<E>> list = new ArrayList<>();
        permuteHelper(list, new ArrayList<>(), arr);
        return list;
    }

    private void permuteHelper(List<List<E>> list, List<E> resultList, List<E> arr) {

        if (resultList.size() == arr.size()) {
            list.add(new ArrayList<>(resultList));
        } else {
            for (int i = 0; i < arr.size(); i++) {

                if (resultList.contains(arr.get(i))) {
                    continue;
                }

                resultList.add(arr.get(i));
                permuteHelper(list, resultList, arr);
                resultList.remove(resultList.size() - 1);
            }
        }
    }


}
