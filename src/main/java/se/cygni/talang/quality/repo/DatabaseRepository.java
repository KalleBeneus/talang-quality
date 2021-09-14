package se.cygni.talang.quality.repo;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import se.cygni.talang.quality.model.Brand;
import se.cygni.talang.quality.model.Dealer;
import se.cygni.talang.quality.model.Vehicle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Repository
public class DatabaseRepository implements Repository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DatabaseRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Vehicle getVehicleByRegistration(String registration) {
        Map<String, String> params = Map.of("registration", registration);
        String sql = "SELECT registrationNumber, owner, brand FROM vehicles WHERE registrationNumber=:registration";

        List<Vehicle> result = jdbcTemplate.query(sql, params, new VehicleRowMapper());
        return resultOrEmpty(result);
    }

    @Override
    public Dealer getDealerByOrgNumber(String orgNumber) {
        Map<String, String> params = Map.of("orgNumber", orgNumber);
        String sql = "SELECT organisationNumber, premiumCustomer FROM dealers WHERE organisationNumber=:orgNumber";
        List<Dealer> result = jdbcTemplate.query(sql, params, new DealerRowMapper());
        return resultOrEmpty(result);
    }

    @Override
    public List<Brand> getPremiumBrands() {
        String sql = "SELECT brand FROM premiumBrands";
        return jdbcTemplate.query(sql, new BrandRowMapper());
    }

    @Override
    public void saveVehicle(Vehicle vehicle) {
        Map<String, String> params = Map.of(
                "regNum", vehicle.getRegistration(),
                "owner", vehicle.getOwner(),
                "brand", vehicle.getBrand().name());
        String sql = "insert into vehicles(registrationNumber, owner, brand) values (:regNum, :owner, :brand) " +
                "   on conflict (registrationNumber) DO " +
                "   UPDATE SET registrationNumber=:regNum, owner=:owner, brand=:brand";
        jdbcTemplate.update(sql, params);
    }

    @Override
    public void saveDealer(Dealer dealer) {
        Map<String, Object> params = Map.of(
                "orgNum", dealer.getOrganisationNumber(),
                "premium", dealer.isPremiumCustomer());
        String sql = "insert into dealers(organisationNumber, premiumCustomer) values (:orgNum, :premium) " +
                "    on conflict (organisationNumber) DO  " +
                "    UPDATE SET organisationNumber=:orgNum, premiumCustomer=:premium";
        jdbcTemplate.update(sql, params);
    }

    public static class VehicleRowMapper implements RowMapper<Vehicle> {
        @Override
        public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
            Vehicle vehicle = new Vehicle(rs.getString("registrationNumber"));
            vehicle.setOwner(rs.getString("owner"));
            vehicle.setBrand(Brand.valueOf(rs.getString("brand")));
            return vehicle;
        }

    }
    public static class DealerRowMapper implements RowMapper<Dealer> {

        @Override
        public Dealer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Dealer dealer = new Dealer(rs.getString("organisationNumber"));
            dealer.setPremiumCustomer(rs.getBoolean("premiumCustomer"));
            return dealer;
        }
    }
    public static class BrandRowMapper implements RowMapper<Brand> {

        @Override
        public Brand mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Brand.valueOf(rs.getString("brand"));
        }
    }

    private <T> T resultOrEmpty(List<T> result) {
        if(result.size() != 1) {
            return null;
        }
        return result.get(0);
    }
}
