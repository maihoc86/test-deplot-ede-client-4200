package com.ede.edesystemservice.controller.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edesystemservice.entity.transportation.ApiName;
import com.ede.edesystemservice.entity.transportation.AvailableService;
import com.ede.edesystemservice.entity.transportation.District;
import com.ede.edesystemservice.entity.transportation.Province;
import com.ede.edesystemservice.entity.transportation.TransportFee;
import com.ede.edesystemservice.entity.transportation.TransportTime;
import com.ede.edesystemservice.entity.transportation.Ward;
import com.ede.edesystemservice.service.TransportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/ede-system/transportation")
public class TransportationRestController {
	
	@Autowired
	@Qualifier("GHN")
	TransportService ghnService;
	
//	@Autowired
//	@Qualifier("GHTK")
//	TransportService ghtkService;
	
	/**
	 * Tra cứu các API Hổ trợ
	 * @return danh sách thông tin api
	 */
	@RequestMapping("/get-api-names")
	List<JsonNode> getApiNames() throws JsonMappingException, JsonProcessingException {
		List<JsonNode> result = new ArrayList<JsonNode>();
		ObjectMapper mapper = new ObjectMapper();
		for (ApiName apiName: ApiName.values()) {
			JsonNode item = mapper.readTree(apiName.toString());
			result.add(item);
		}
		return result;
	}

	/**
	 * Tra cứu tỉnh thành và id tỉnh (theo api)
	 * @return danh sách các tỉnh thành
	 */
	@RequestMapping("/{apiName}/get-provices")
	List<Province> getProvices(@PathVariable("apiName") ApiName apiName ) {
		return this.getTransportSrv(apiName).getProvices();
	}

	/**
	 * Tra cứu quận/huyện (theo api)
	 * @param proviceId Mã tỉnh/ thành phố
	 * @return Danh sách các quận/huyện
	 */
	@RequestMapping("/{apiName}/get-districts")
	List<District> getDistricts(
			@RequestParam("province_id") Optional<Integer> provinceId, 
			@PathVariable("apiName") ApiName apiName) {
		return this.getTransportSrv(apiName).getDistricts(provinceId);
	}
	
	/**
	 * Tra cứu phường/xã (theo api)
	 * @param districtId Mã quận/huyện
	 * @return danh sách các xã
	 * @see #getProvices()
	 */
	@RequestMapping("/{apiName}/get-wards")
	List<Ward> getWards(
			@RequestParam(value="district_id", required = true) Optional<Integer> districtId,
			@PathVariable("apiName") ApiName apiName) {
		return this.getTransportSrv(apiName).getWards(districtId);
	}
	
	/**
	 * Tra cứu các hình thức vận chuyển (đi bộ, máy bay, đường thuỷ)
	 * @param fromDistrictId Id quận/huyện gửi hàng
	 * @param toDistrictId Id quận/huyện nhận hàng
	 * @return Danh sách các phương thức có thể chọn
	 * @see #getDistricts(Optional)
	 */
	@RequestMapping("/{apiName}/get-available-services")
	List<AvailableService> getAvailableServices(
			@RequestParam(value = "from_district", required = true) int fromDistrictId,
			@RequestParam(value = "to_district", required = true) int toDistrictId,
			@PathVariable("apiName") ApiName apiName) {
		return this.getTransportSrv(apiName).getAvailableServices(fromDistrictId, toDistrictId);
	}

	/**
	 * Tính tiền - Tính chi phí vận chuyển cho khách hàng
	 * @param fromDistrictId Id quận/huyện gửi hàng
	 * @param toDistrictId Id quận/huyện nhận hàng
	 * @param serviceId Hình thức vận chuyển (đi bộ, đi máy bay) xem hàm {@link #getAvailableServices(int, int)}
	 * @param weight Cân nặng của món hàng <b>Đơn vị gram</b>
	 * @return Danh sách các phương pháp vận chuyển
	 * @see #getDistricts(Optional)
	 */
	@RequestMapping("/{apiName}/get-transport-fee")
	TransportFee getTransportFee(
			@RequestParam(value = "from_district", required = true) int fromDistrictId, 
			@RequestParam(value = "to_district", required = true) int toDistrictId,
			@RequestParam(value = "service_id", required = true) int serviceId,
			@RequestParam(value = "weight", required = true) float weight,
			@PathVariable("apiName") ApiName apiName) {
		return this.getTransportSrv(apiName).getTransportFee(fromDistrictId, toDistrictId, serviceId, weight);
	}
	
	/**
	 * Tính giờ - tính toán thời gian đơn hàng vận chuyển
	 * @param fromDistrictId Quận/Huyện gửi
	 * @param fromWardId Phường/Xã gửi
	 * @param toDistrictId Quận/Huyện nhận
	 * @param toWardId Phường/Xã nhận
	 * @param serviceId Phương pháp vận chuyển
	 * @return Trả về ngày order và ngày nhận dự kiến (nếu chưa order, ngày order là thời gian unix) Ngày tháng sữ dụng thời gian unix thay vì format 
	 */
	@RequestMapping("/{apiName}/get-transport-time")
	TransportTime getTransportTime(
			@RequestParam(value = "from_district", required = true) int fromDistrictId,
			@RequestParam(value = "from_ward", required = true) String fromWardId,
			@RequestParam(value = "to_district", required = true) int toDistrictId,
			@RequestParam(value = "to_ward", required = true) String toWardId,
			@RequestParam(value = "service_id", required = true) int serviceId,
			@PathVariable("apiName") ApiName apiName) {
		return this.getTransportSrv(apiName).getTransportTime(fromDistrictId, fromWardId, toDistrictId, toWardId, serviceId);
	}
	
	private TransportService getTransportSrv(ApiName apiName) {
		switch(apiName) {
			case GHN:
				return ghnService;
			case GHTK:
				return ghnService;
			default:
				throw new UnsupportedOperationException("Không hổ trợ api vận chuyển: " + apiName);
		}
	}
	
}
