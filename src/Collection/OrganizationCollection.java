package Collection;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class OrganizationCollection extends LinkedHashSet<Organization> {
    private int ID_COUNTER = 0;

    public int getID_COUNTER() {
        return ID_COUNTER;
    }

    public void setID_COUNTER(int id){
        this.ID_COUNTER = id;
    }

    long initDate = 0;

    /**
     * adds an organization into collection
     *
     * @param organization Organization which should be added into collection
     * @return true if added successfully, otherwise - false
     */
    public boolean add(Organization organization) {
        organization.setId(++ID_COUNTER);
        return super.add(organization);
    }

    /**
     * Updates the Organization with provided id with new one
     *
     * @param id           Organization's id
     * @param organization new Organization
     */
    public void updateById(int id, Organization organization) {
        stream().filter(organization1 -> organization1.getId() == id)
                .findFirst().ifPresent(org -> {
                    organization.setId(id);
                    organization.setCreationDate(org.getCreationDate());
                    remove(org);
                });
        add(organization);
    }

    /**
     * removes the organization with provided id
     *
     * @param id Organization id
     */
    public void removeById(int id) {
        stream().filter(organization1 -> organization1.getId() == id)
                .findFirst().ifPresent(this::remove);
        ID_COUNTER--;
    }

    /**
     * adds organization into the collection if provided organization has fewer value of annual turnover
     *
     * @param organization Organization that will be added into the collection
     */
    public boolean addIfMax(Organization organization) {
        Float maxValue = stream().max(Comparator.comparing(Organization::getAnnualTurnover))
                .map(Organization::getAnnualTurnover).orElse(Float.valueOf(-1));
        if (organization.getAnnualTurnover() > maxValue) {
            return add(organization);
        } else {
            return false;
        }
    }

    /**
     * Removes all the elements that have annual turnover value less than provided
     *
     * @param organization Organization to compare with
     */
    public void removeLower(Organization organization) {
        removeAll(stream().filter(organization1 -> organization1.getAnnualTurnover() < organization.getAnnualTurnover())
                .collect(Collectors.toSet()));
    }

    /**
     * Returns all elements that have the same annual turnover as provided one
     *
     * @param annualTurnover annual turnover value to compare with organizations
     * @return organization that has provided annual turnover
     */
    public List<Organization> filter_by_annualTurnover(float annualTurnover) {
        return stream().filter(organization -> organization.getAnnualTurnover() == annualTurnover)
                .distinct().collect(Collectors.toList());
    }

    /**
     * Returns all elements that have the same full name as the provided one
     *
     * @param fullName full name value to compare
     * @return organization that has provided full name
     */
    public List<Organization> filter_by_fullName(String fullName) {
        return stream().filter(organization -> organization.getFullName().contains(fullName))
                .distinct().collect(Collectors.toList());
    }

    public long getInitDate() {
        initDate = System.currentTimeMillis();
        return initDate;
    }
}
