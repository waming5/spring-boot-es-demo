package com.hose.mall.search;

import java.io.Serializable;
import java.util.List;

/**
 * 酒店详情
 *
 * @author wangmi wangmi@hosecloud.com
 */
public class HotelVo implements Serializable {
    private static final long serialVersionUID = 9037712491545438506L;

    public HotelVo() {
        super();
    }

    /**
     * 酒店编号（主键）
     */
    private String hotelNo;
    /**
     * 酒店中文名称
     */
    private String nameCn;
    /**
     * 酒店英文名称
     */
    private String nameEn;
    /**
     * 中文地址
     */
    private String addressCn;
    /**
     * 英文地址
     */
    private String addressEn;
    /**
     * 邮编
     */
    private String postalCode;
    /**
     * 挂牌星级。 此为酒店对外的挂牌星级，0-无星级；1-一星级；2-二星级；3-三星级；4-四星级；5-五星级。当为0时对外显示可用Category的值，但请进行图标区分
     */
    private Integer starRate;
    /**
     * 推荐星级 推荐星级，而非酒店挂牌星级。对应值含义为：0:民宿（农家院、农家乐）；1:公寓; 2：经济；3：舒适；4：高档；5：豪华。
     */
    private Integer category;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 品牌编号
     */
    private String brandId;
    /**
     * 城市
     */
    private String geoId;
    /**
     * 关联城市
     */
    private String geoId2;
    /**
     * 行政区
     */
    private String district;
    /**
     * 主商圈
     */
    private String businessZone;
    /**
     * 附属商圈
     */
    private String businessZone2;
    /**
     * 设施列表 这个列表用于搜索 1、免费wifi 2、收费wifi 3、免费宽带 4、收费宽带 5、免费停车场 6、收费停车场 7、免费接机服务 8、收费接机服务 9、室内游泳池 10、室外游泳池 11、健身房 12、商务中心
     * 13、会议室 14、酒店餐厅 15、叫醒服务 16、提供发票 17、租车服务 18、洗衣服务
     */
    private String facilities;
    /**
     * 主题
     */
    private String themes;
    /**
     * 省份代码
     */
    private String province;
    /**
     * 客房总数量
     */
    private String roomTotal;
    /**
     * 酒店状态 为空默认为OPEN。OPEN: 可以销售；CLOSE: 已经关闭
     */
    private String hotelStatus;
    /**
     * 评分
     */
    private Float score;
    /**
     * 排序评分
     */
    private Float sortScore;
    /**
     * 封面图片
     */
    private String coverImage;
    /**
     * 酒店最低价格 作为参考排序使用
     */
    private Double lowRate;
    /**
     * Geo Hash 例如：距您直线 2000 m
     **/
    private String geohash;
    /**
     * 酒店服务评分
     **/
    private ServiceScoreVo serviceScore;
    /**
     * 酒店适配
     **/
    private List<DetailAdapterVo> adapters;
    /**** 关联结算方式 1--公司支付 0--个人支付 ***/
    private String settle;
    /**
     * 交通
     **/
    private String traffic;
    /**
     * 行政区
     **/
    private String districtName;
    /**
     * 设施
     **/
    private String generalAmenitiesCn;
    /***** 标记类型 P-协议 D-直销 ***/
    private String tags;
    /***** 协议自签映射 ******/
    private List<ProtocolMappingVo> protocols;

    public String getHotelNo() {
        return this.hotelNo;
    }

    public void setHotelNo(String hotelNo) {
        this.hotelNo = hotelNo;
    }

    public String getNameCn() {
        return this.nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getNameEn() {
        return this.nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getAddressCn() {
        return this.addressCn;
    }

    public void setAddressCn(String addressCn) {
        this.addressCn = addressCn;
    }

    public String getAddressEn() {
        return this.addressEn;
    }

    public void setAddressEn(String addressEn) {
        this.addressEn = addressEn;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getStarRate() {
        return this.starRate;
    }

    public void setStarRate(Integer starRate) {
        this.starRate = starRate;
    }

    public Integer getCategory() {
        return this.category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getGeoId() {
        return this.geoId;
    }

    public void setGeoId(String geoId) {
        this.geoId = geoId;
    }

    public String getGeoId2() {
        return this.geoId2;
    }

    public void setGeoId2(String geoId2) {
        this.geoId2 = geoId2;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getBusinessZone() {
        return this.businessZone;
    }

    public void setBusinessZone(String businessZone) {
        this.businessZone = businessZone;
    }

    public String getBusinessZone2() {
        return this.businessZone2;
    }

    public void setBusinessZone2(String businessZone2) {
        this.businessZone2 = businessZone2;
    }

    public String getFacilities() {
        return this.facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public String getThemes() {
        return this.themes;
    }

    public void setThemes(String themes) {
        this.themes = themes;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRoomTotal() {
        return this.roomTotal;
    }

    public void setRoomTotal(String roomTotal) {
        this.roomTotal = roomTotal;
    }

    public String getHotelStatus() {
        return this.hotelStatus;
    }

    public void setHotelStatus(String hotelStatus) {
        this.hotelStatus = hotelStatus;
    }

    public String getCoverImage() {
        return this.coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public Double getLowRate() {
        return this.lowRate;
    }

    public void setLowRate(Double lowRate) {
        this.lowRate = lowRate;
    }

    public Float getScore() {
        return this.score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Float getSortScore() {
        return this.sortScore;
    }

    public void setSortScore(Float sortScore) {
        this.sortScore = sortScore;
    }

    public String getGeohash() {
        return geohash;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    public ServiceScoreVo getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(ServiceScoreVo serviceScore) {
        this.serviceScore = serviceScore;
    }

    public List<DetailAdapterVo> getAdapters() {
        return adapters;
    }

    public void setAdapters(List<DetailAdapterVo> adapters) {
        this.adapters = adapters;
    }

    public String getSettle() {
        return settle;
    }

    public void setSettle(String settle) {
        this.settle = settle;
    }


    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getGeneralAmenitiesCn() {
        return generalAmenitiesCn;
    }

    public void setGeneralAmenitiesCn(String generalAmenitiesCn) {
        this.generalAmenitiesCn = generalAmenitiesCn;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<ProtocolMappingVo> getProtocols() {
        return protocols;
    }

    public void setProtocols(List<ProtocolMappingVo> protocols) {
        this.protocols = protocols;
    }

    @Override
    public int hashCode() {
        int num = 31;
        num = num * hotelNo.hashCode();
        return num;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        HotelVo hotel = (HotelVo) obj;
        if (this.getHotelNo().equals(hotel.getHotelNo())) {
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        return "HotelDTO [hotelNo=" + hotelNo + ", nameCn=" + nameCn + ", nameEn=" + nameEn + ", addressCn=" + addressCn
                + ", addressEn=" + addressEn + ", postalCode=" + postalCode + ", starRate=" + starRate + ", category="
                + category + ", phone=" + phone + ", longitude=" + longitude + ", latitude=" + latitude + ", brandId="
                + brandId + ", geoId=" + geoId + ", geoId2=" + geoId2 + ", district=" + district + ", businessZone="
                + businessZone + ", businessZone2=" + businessZone2 + ", facilities=" + facilities + ", themes=" + themes
                + ", province=" + province + ", roomTotal=" + roomTotal + ", hotelStatus=" + hotelStatus + ", score="
                + score + ", sortScore=" + sortScore + ", coverImage=" + coverImage + ", lowRate=" + lowRate + ", geohash="
                + geohash + ", serviceScore=" + serviceScore + ", adapters=" + adapters + ", settle=" + settle + ", tags="
                + tags + ", protocols=" + protocols + "]";
    }

}
