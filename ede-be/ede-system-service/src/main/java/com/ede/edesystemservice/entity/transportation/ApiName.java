package com.ede.edesystemservice.entity.transportation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiName {
	
	GHN("GHN", "Giao hàng nhanh"),
	GHTK("GHTK", "Giao hàng tiết kiệm");
	
	private final String id;
	private final String name;
	
	/**
	 * Hàm này override lại để xuất enum ra json, <b>đừng sửa khi không cần</b>
	 */
	@Override
	public String toString() {
		return String.format("{\"id\": \"%s\", \"name\": \"%s\"}", this.id, this.name);
	}
	
}
