package com.northwind.entities;

public class EmployeeTerritory {
    public String territoryId;
    public String territoryDescription;
    public TerritoryRegion territoryRegion;

    public String getTerritoryId() {
        return territoryId;
    }

    public void setTerritoryId(String territoryId) {
        this.territoryId = territoryId;
    }

    public String getTerritoryDescription() {
        return territoryDescription;
    }

    public void setTerritoryDescription(String territoryDescription) {
        this.territoryDescription = territoryDescription;
    }

    public TerritoryRegion getTerritoryRegion() {
        return territoryRegion;
    }

    public void setTerritoryRegion(TerritoryRegion territoryRegion) {
        this.territoryRegion = territoryRegion;
    }
}
