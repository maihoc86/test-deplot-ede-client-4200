package com.ede.edesystemservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ede.edesystemservice.entity.transportation.AvailableService;
import com.ede.edesystemservice.entity.transportation.District;
import com.ede.edesystemservice.entity.transportation.Province;
import com.ede.edesystemservice.entity.transportation.TransportFee;
import com.ede.edesystemservice.entity.transportation.TransportTime;
import com.ede.edesystemservice.entity.transportation.Ward;

/**
 * Các phương thức vận chuyển (api)
 * @author vinh
 *
 */
@Service
public interface TransportService {
	/**
	 * Tra cứu tỉnh thành và id tỉnh (theo api)
	 * @return danh sách các tỉnh thành
	 */
	List<Province> getProvices();
	/**
	 * Tra cứu quận/huyện (theo api)
	 * @param proviceId Mã tỉnh/ thành phố
	 * @return Danh sách các quận/huyện
	 */
	List<District> getDistricts(Optional<Integer> proviceId);
	/**
	 * Tra cứu phường/xã (theo api)
	 * @param districtId Mã quận/huyện
	 * @return danh sách các xã
	 * @see #getProvices()
	 */
	List<Ward> getWards(Optional<Integer> districtId);
	/**
	 * Tra cứu các hình thức vận chuyển (đi bộ, máy bay, đường thuỷ)
	 * @param fromDistrictId Id quận/huyện gửi hàng
	 * @param toDistrictId Id quận/huyện nhận hàng
	 * @return Danh sách các phương thức có thể chọn
	 * @see #getDistricts(Optional)
	 */
	List<AvailableService> getAvailableServices(int fromDistrictId, int toDistrictId);
	/**
	 * Tính tiền - Tính chi phí vận chuyển cho khách hàng
	 * @param fromDistrictId Id quận/huyện gửi hàng
	 * @param toDistrictId Id quận/huyện nhận hàng
	 * @param serviceId Hình thức vận chuyển (đi bộ, đi máy bay) xem hàm {@link #getAvailableServices(int, int)}
	 * @param weight Cân nặng của món hàng
	 * @return Danh sách các phương pháp vận chuyển
	 * @see #getDistricts(Optional)
	 */
	TransportFee getTransportFee(int fromDistrictId, int toDistrictId, int serviceId, float weight);
	/**
	 * Tính giờ - tính toán thời gian đơn hàng vận chuyển
	 * @param fromDistrictId Quận/Huyện gửi
	 * @param fromWardId Phường/Xã gửi
	 * @param toDistrictId Quận/Huyện nhận
	 * @param toWardId Phường/Xã nhận
	 * @param serviceId Phương pháp vận chuyển
	 * @return Trả về ngày order và ngày nhận dự kiến (nếu chưa order, ngày order là thời gian unix) Ngày tháng sữ dụng thời gian unix thay vì format 
	 */
	TransportTime getTransportTime(int fromDistrictId, String fromWardId, int toDistrictId, String toWardId, int serviceId);
}
