package ru.lab.regionapp.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Вспомогательный класс для сохранения списков областей и районов в XML.
 */
@XmlRootElement(name = "data")
public class RegionDataWrapper {

    private List<Region> regions = new ArrayList<>();
    private List<District> districts = new ArrayList<>();

    @XmlElementWrapper(name = "regions")
    @XmlElement(name = "region")
    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    @XmlElementWrapper(name = "districts")
    @XmlElement(name = "district")
    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }
}
