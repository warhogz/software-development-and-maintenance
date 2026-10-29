package variant13;

/** Условие отбора районов */
public interface DistrictFilter {
    boolean accept(District district);
}
