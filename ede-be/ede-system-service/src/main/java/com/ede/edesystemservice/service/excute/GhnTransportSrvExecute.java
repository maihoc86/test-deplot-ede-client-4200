package com.ede.edesystemservice.service.excute;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ede.edesystemservice.entity.transportation.AvailableService;
import com.ede.edesystemservice.entity.transportation.District;
import com.ede.edesystemservice.entity.transportation.Province;
import com.ede.edesystemservice.entity.transportation.TransportFee;
import com.ede.edesystemservice.entity.transportation.TransportTime;
import com.ede.edesystemservice.entity.transportation.Ward;
import com.ede.edesystemservice.service.TransportService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;

@Service("GHN")
public class GhnTransportSrvExecute implements TransportService {

	private final String TOKEN = "d9317f77-3790-11ec-b514-aeb9e8b0c5e3";
	private final String SHOP_ID = "82769";

	private final String URL_GET_PROVICES = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province";
	private final String URL_GET_DISTRICTS = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district"; // province_id=216
	private final String URL_GET_WARDS = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward"; // district_id=1725";
	private final String URL_GET_AVAILABLE_SERVICES = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/available-services";// ?from_district=3200&to_district=1564&shop_id=82769";
	private final String URL_GET_TRANSPORT_FEE = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";// ?from_district_id=3200&to_district_id=1564&weight=5&service_id=53320";
	private final String URL_GET_TRANSPORT_DATE = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/leadtime";// ?from_district_id=1750&from_ward_code=511110&to_district_id=1750&to_ward_code=511110&service_id=53320";

	private JsonMapper jsonMapper = new JsonMapper();

	@Override
	public List<Province> getProvices() {
		List<Province> result = new ArrayList<Province>();
		try {
			URL url = new URL(URL_GET_PROVICES);
			URLConnection connect = url.openConnection();
			connect.addRequestProperty("token", TOKEN);
			JsonNode response = jsonMapper.readTree(connect.getInputStream());
			for (JsonNode province : response.get("data")) {
				String id = province.get("ProvinceID").asText();
				String name = province.get("ProvinceName").asText();
				result.add(new Province(id, name));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<District> getDistricts(Optional<Integer> proviceId) {
		String urlGetDistricts = URL_GET_DISTRICTS;
		if (proviceId.isPresent()) {
			urlGetDistricts += ("?province_id=" + proviceId.get());
		}
		List<District> result = new ArrayList<District>();
		try {
			URL url = new URL(urlGetDistricts);
			URLConnection connect = url.openConnection();
			connect.addRequestProperty("token", TOKEN);
			JsonNode response = jsonMapper.readTree(connect.getInputStream());
			for (JsonNode district : response.get("data")) {
				String id = district.get("DistrictID").asText();
				String idProvince = district.get("ProvinceID").asText();
				String name = district.get("DistrictName").asText();
				result.add(new District(id, idProvince, name));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Ward> getWards(Optional<Integer> districtId) {
		if (!districtId.isPresent()) {
			throw new UnsupportedOperationException(
					"Giao hàng nhanh Không hổ trợ truy xuất thông tin các phường/xã, mà không có thông tin quận/huyện kèm theo");
		}
		String urlGetWards = URL_GET_WARDS.concat("?district_id=" + districtId.get());
		List<Ward> result = new ArrayList<Ward>();
		try {
			URL url = new URL(urlGetWards);
			URLConnection connect = url.openConnection();
			connect.addRequestProperty("token", TOKEN);
			JsonNode response = jsonMapper.readTree(connect.getInputStream());
			for (JsonNode ward : response.get("data")) {
				String id = ward.get("WardCode").asText();
				String idDistrict = ward.get("DistrictID").asText();
				String name = ward.get("WardName").asText();
				result.add(new Ward(id, idDistrict, name));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<AvailableService> getAvailableServices(int fromDistrictId, int toDistrictId) {
		String urlGetAvailableService = String.format("%s?from_district=%d&to_district=%d&shop_id=%s",
				URL_GET_AVAILABLE_SERVICES, fromDistrictId, toDistrictId, SHOP_ID);
		List<AvailableService> result = new ArrayList<AvailableService>();
		try {
			URL url = new URL(urlGetAvailableService);
			URLConnection connect = url.openConnection();
			connect.addRequestProperty("token", TOKEN);
			JsonNode response = jsonMapper.readTree(connect.getInputStream());
			for (JsonNode availableService : response.get("data")) {
				String id = availableService.get("service_id").asText();
				String name = availableService.get("short_name").asText();
				if ("".equalsIgnoreCase(name)) {
					continue;
				}
				result.add(new AvailableService(id, name));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public TransportFee getTransportFee(int fromDistrictId, int toDistrictId, int serviceId, float weight) {
		String urlGetFee = String.format("%s?from_district_id=%d&to_district_id=%d&weight=%.0f&service_id=%d",
				URL_GET_TRANSPORT_FEE, fromDistrictId, toDistrictId, weight, serviceId);
		TransportFee result = null;
		try {
			URL url = new URL(urlGetFee);
			URLConnection connect = url.openConnection();
			connect.addRequestProperty("token", TOKEN);
			JsonNode response = jsonMapper.readTree(connect.getInputStream());
			float total = Float.parseFloat(response.get("data").get("total").asText());
			result = new TransportFee(total);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public TransportTime getTransportTime(int fromDistrictId, String fromWardId, int toDistrictId, String toWardId,
			int serviceId) {
		String urlGetTime = String.format(
				"%s?" + "from_district_id=%d&" + "from_ward_code=%s&" + "to_district_id=%d&" + "to_ward_code=%s&"
						+ "service_id=%d",
				URL_GET_TRANSPORT_DATE, fromDistrictId, fromWardId, toDistrictId, toWardId, serviceId);
		TransportTime result = null;
		try {
			URL url = new URL(urlGetTime);
			URLConnection connect = url.openConnection();
			connect.addRequestProperty("token", TOKEN);
			JsonNode response = jsonMapper.readTree(connect.getInputStream());
			long orderTime = Long.parseLong(response.get("data").get("order_date").asText());
			long receiveTime = Long.parseLong(response.get("data").get("leadtime").asText());
			result = new TransportTime(orderTime, receiveTime);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
