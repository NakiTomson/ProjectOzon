package com.appmarketplace.ozon.data.remote.modelsAPI

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class ProductsModel(


        @SerializedName("totalPages")
        @Expose
        val totalPages: Int? = null,

        @SerializedName("products")
        @Expose val products: List<Product>? = null
) {}
//data class CategoryPath(
//        @SerializedName("id")
//        @Expose
//        var id: String? = null,
//
//        @SerializedName("name")
//        @Expose
//        var name: String? = null,
//) {}
//data class Image(
//        @SerializedName("rel")
//        @Expose
//        var rel: String? = null,
//
//        @SerializedName("unitOfMeasure")
//        @Expose
//        var unitOfMeasure: String? = null,
//
//        @SerializedName("width")
//        @Expose
//        var width: String? = null,
//
//        @SerializedName("height")
//        @Expose
//        var height: String? = null,
//
//        @SerializedName("href")
//        @Expose
//        var href: String? = null,
//
//        @SerializedName("primary")
//        @Expose
//        var primary: Boolean? = null,
//){}
//data class IncludedItemList(
//        @SerializedName("includedItem")
//        @Expose
//        var includedItem: String? = null,
//){}
//data class Shipping(
//
//        @SerializedName("ground")
//        @Expose
//        var ground: String? = null,
//
//        @SerializedName("secondDay")
//        @Expose
//        var secondDay: String? = null,
//
//        @SerializedName("nextDay")
//        @Expose
//        var nextDay: String? = null,
//
//        @SerializedName("vendorDelivery")
//        @Expose
//        var vendorDelivery: String? = null,
//) {}
//data class ShippingLevelsOfService(
//
//        @SerializedName("serviceLevelId")
//        @Expose
//        var serviceLevelId: Int? = null,
//
//        @SerializedName("serviceLevelName")
//        @Expose
//        var serviceLevelName: String? = null,
//
//        @SerializedName("unitShippingPrice")
//        @Expose
//        var unitShippingPrice: Double? = null,
//) {}
data class Product(
//        @SerializedName("sku")
//        @Expose
//        var sku: Int? = null,
//
//        @SerializedName("score")
//        @Expose
//        var score: Any? = null,
//
//        @SerializedName("productId")
//        @Expose
//        var productId: Any? = null,
//
        @SerializedName("name")
        @Expose
        var name: String? = null,
//
//        @SerializedName("source")
//        @Expose
//        var source: Any? = null,
//
//        @SerializedName("type")
//        @Expose
//        var type: String? = null,
//
//        @SerializedName("startDate")
//        @Expose
//        var startDate: String? = null,
//
//        @SerializedName("new")
//        @Expose
//        var new: Boolean? = null,
//
//        @SerializedName("active")
//        @Expose
//        var active: Boolean? = null,
//
//        @SerializedName("lowPriceGuarantee")
//        @Expose
//        var lowPriceGuarantee: Boolean? = null,
//
//        @SerializedName("activeUpdateDate")
//        @Expose
//        var activeUpdateDate: String? = null,

        @SerializedName("regularPrice")
        @Expose
        var regularPrice: Double? = null,

        @SerializedName("salePrice")
        @Expose
        var salePrice: Double? = null,
//
//        @SerializedName("clearance")
//        @Expose
//        var clearance: Boolean? = null,
//
//        @SerializedName("onSale")
//        @Expose
//        var onSale: Boolean? = null,
//
//        @SerializedName("planPrice")
//        @Expose
//        var planPrice: Any? = null,
//
//        @SerializedName("priceWithPlan")
//        @Expose
//        var priceWithPlan: List<Any>? = null,
//
//        @SerializedName("contracts")
//        @Expose
//        var contracts: List<Any>? = null,

//        @SerializedName("priceRestriction")
//        @Expose
//        var priceRestriction: Any? = null,
//
//        @SerializedName("priceUpdateDate")
//        @Expose
//        var priceUpdateDate: String? = null,
//
//        @SerializedName("digital")
//        @Expose
//        var digital: Boolean? = null,
//
//        @SerializedName("preowned")
//        @Expose
//        var preowned: Boolean? = null,
//
//        @SerializedName("carriers")
//        @Expose
//        var carriers: List<Any>? = null,
//
//        @SerializedName("planFeatures")
//        @Expose
//        var planFeatures: List<Any>? = null,
//
//        @SerializedName("devices")
//        @Expose
//        var devices: List<Any>? = null,
//
//        @SerializedName("carrierPlans")
//        @Expose
//        var carrierPlans: List<Any>? = null,
//
//        @SerializedName("technologyCode")
//        @Expose
//        var technologyCode: Any? = null,
//
//        @SerializedName("carrierModelNumber")
//        @Expose
//        var carrierModelNumber: Any? = null,
//
//        @SerializedName("earlyTerminationFees")
//        @Expose
//        var earlyTerminationFees: List<Any>? = null,
//
//        @SerializedName("monthlyRecurringCharge")
//        @Expose
//        var monthlyRecurringCharge: String? = null,
//
//        @SerializedName("monthlyRecurringChargeGrandTotal")
//        @Expose
//        var monthlyRecurringChargeGrandTotal: String? = null,
//
//        @SerializedName("activationCharge")
//        @Expose
//        var activationCharge: String? = null,
//
//        @SerializedName("minutePrice")
//        @Expose
//        var minutePrice: String? = null,
//
//        @SerializedName("planCategory")
//        @Expose
//        var planCategory: Any? = null,
//
//        @SerializedName("planType")
//        @Expose
//        var planType: Any? = null,
//
//        @SerializedName("familyIndividualCode")
//        @Expose
//        var familyIndividualCode: Any? = null,
//
//        @SerializedName("validFrom")
//        @Expose
//        var validFrom: Any? = null,
//
//        @SerializedName("validUntil")
//        @Expose
//        var validUntil: Any? = null,
//
//        @SerializedName("carrierPlan")
//        @Expose
//        var carrierPlan: Any? = null,
//
//        @SerializedName("outletCenter")
//        @Expose
//        var outletCenter: Any? = null,
//
//        @SerializedName("secondaryMarket")
//        @Expose
//        var secondaryMarket: Any? = null,
//
//        @SerializedName("frequentlyPurchasedWith")
//        @Expose
//        var frequentlyPurchasedWith: List<Any>? = null,
//
//        @SerializedName("accessories")
//        @Expose
//        var accessories: List<Any>? = null,
//
//        @SerializedName("relatedProducts")
//        @Expose
//        var relatedProducts: List<Any>? = null,
//
//        @SerializedName("requiredParts")
//        @Expose
//        var requiredParts: List<Any>? = null,
//
//        @SerializedName("techSupportPlans")
//        @Expose
//        var techSupportPlans: List<Any>? = null,
//
//        @SerializedName("crossSell")
//        @Expose
//        var crossSell: List<Any>? = null,
//
//        @SerializedName("salesRankShortTerm")
//        @Expose
//        var salesRankShortTerm: Any? = null,
//
//        @SerializedName("salesRankMediumTerm")
//        @Expose
//        var salesRankMediumTerm: Any? = null,
//
//        @SerializedName("salesRankLongTerm")
//        @Expose
//        var salesRankLongTerm: Any? = null,
//
//        @SerializedName("bestSellingRank")
//        @Expose
//        var bestSellingRank: Any? = null,
//
//        @SerializedName("url")
//        @Expose
//        var url: String? = null,
//
//        @SerializedName("spin360Url")
//        @Expose
//        var spin360Url: Any? = null,
//
//        @SerializedName("mobileUrl")
//        @Expose
//        var mobileUrl: String? = null,
//
//        @SerializedName("affiliateUrl")
//        @Expose
//        var affiliateUrl: Any? = null,
//
//        @SerializedName("addToCartUrl")
//        @Expose
//        var addToCartUrl: String? = null,
//
//        @SerializedName("affiliateAddToCartUrl")
//        @Expose
//        var affiliateAddToCartUrl: Any? = null,
//
//        @SerializedName("linkShareAffiliateUrl")
//        @Expose
//        var linkShareAffiliateUrl: String? = null,
//
//        @SerializedName("linkShareAffiliateAddToCartUrl")
//        @Expose
//        var linkShareAffiliateAddToCartUrl: String? = null,
//
//        @SerializedName("upc")
//        @Expose
//        var upc: String? = null,
//
//        @SerializedName("productTemplate")
//        @Expose
//        var productTemplate: String? = null,

//        @SerializedName("categoryPath")
//        @Expose
//        var categoryPath: List<CategoryPath>? = null,

//        @SerializedName("alternateCategories")
//        @Expose
//        var alternateCategories: List<Any>? = null,

//        @SerializedName("lists")
//        @Expose
//        var lists: List<Any>? = null,

//        @SerializedName("customerReviewCount")
//        @Expose
//        var customerReviewCount: Int? = null,
//
//        @SerializedName("customerReviewAverage")
//        @Expose
//        var customerReviewAverage: Double? = null,
//
//        @SerializedName("customerTopRated")
//        @Expose
//        var customerTopRated: Boolean? = null,
//
//        @SerializedName("format")
//        @Expose
//        var format: Any? = null,
//
//        @SerializedName("freeShipping")
//        @Expose
//        var freeShipping: Boolean? = null,
//
//        @SerializedName("freeShippingEligible")
//        @Expose
//        var freeShippingEligible: Boolean? = null,
//
//        @SerializedName("inStoreAvailability")
//        @Expose
//        var inStoreAvailability: Boolean? = null,
//
//        @SerializedName("inStoreAvailabilityText")
//        @Expose
//        var inStoreAvailabilityText: Any? = null,
//
//        @SerializedName("inStoreAvailabilityUpdateDate")
//        @Expose
//        var inStoreAvailabilityUpdateDate: String? = null,
//
//        @SerializedName("itemUpdateDate")
//        @Expose
//        var itemUpdateDate: String? = null,
//
//        @SerializedName("onlineAvailability")
//        @Expose
//        var onlineAvailability: Boolean? = null,
//
//        @SerializedName("onlineAvailabilityText")
//        @Expose
//        var onlineAvailabilityText: Any? = null,
//
//        @SerializedName("onlineAvailabilityUpdateDate")
//        @Expose
//        var onlineAvailabilityUpdateDate: String? = null,
//
//        @SerializedName("releaseDate")
//        @Expose
//        var releaseDate: Any? = null,
//
//        @SerializedName("shippingCost")
//        @Expose
//        var shippingCost: Int? = null,

//        @SerializedName("shipping")
//        @Expose
//        var shipping: List<Shipping>? = null,

//        @SerializedName("shippingLevelsOfService")
//        @Expose
//        var shippingLevelsOfService: List<ShippingLevelsOfService>? = null,

//        @SerializedName("specialOrder")
//        @Expose
//        var specialOrder: Boolean? = null,
//
//        @SerializedName("shortDescription")
//        @Expose
//        var shortDescription: String? = null,
//
//        @SerializedName("class")
//        @Expose
//        var class_: String? = null,
//
//        @SerializedName("classId")
//        @Expose
//        var classId: Int? = null,
//
//        @SerializedName("subclass")
//        @Expose
//        var subclass: String? = null,
//
//        @SerializedName("subclassId")
//        @Expose
//        var subclassId: Int? = null,
//
//        @SerializedName("department")
//        @Expose
//        var department: String? = null,
//
//        @SerializedName("departmentId")
//        @Expose
//        var departmentId: Int? = null,
//
//        @SerializedName("protectionPlanTerm")
//        @Expose
//        var protectionPlanTerm: String? = null,
//
//        @SerializedName("protectionPlanType")
//        @Expose
//        var protectionPlanType: Any? = null,
//
//        @SerializedName("protectionPlanLowPrice")
//        @Expose
//        var protectionPlanLowPrice: String? = null,
//
//        @SerializedName("protectionPlanHighPrice")
//        @Expose
//        var protectionPlanHighPrice: String? = null,
//
//        @SerializedName("buybackPlans")
//        @Expose
//        var buybackPlans: List<Any>? = null,
//
//        @SerializedName("protectionPlans")
//        @Expose
//        var protectionPlans: List<Any>? = null,
//
//        @SerializedName("protectionPlanDetails")
//        @Expose
//        var protectionPlanDetails: List<Any>? = null,
//
//        @SerializedName("productFamilies")
//        @Expose
//        var productFamilies: List<Any>? = null,
//
//        @SerializedName("productVariations")
//        @Expose
//        var productVariations: List<Any>? = null,
//
//        @SerializedName("aspectRatio")
//        @Expose
//        var aspectRatio: Any? = null,
//
//        @SerializedName("screenFormat")
//        @Expose
//        var screenFormat: Any? = null,
//
//        @SerializedName("lengthInMinutes")
//        @Expose
//        var lengthInMinutes: Any? = null,
//
//        @SerializedName("mpaaRating")
//        @Expose
//        var mpaaRating: Any? = null,
//
//        @SerializedName("plot")
//        @Expose
//        var plot: Any? = null,
//
//        @SerializedName("studio")
//        @Expose
//        var studio: Any? = null,
//
//        @SerializedName("theatricalReleaseDate")
//        @Expose
//        var theatricalReleaseDate: Any? = null,

        @SerializedName("description")
        @Expose
        var description: Any? = null,

//        @SerializedName("manufacturer")
//        @Expose
//        var manufacturer: String? = null,

        @SerializedName("modelNumber")
        @Expose
        var modelNumber: String? = null,

//        @SerializedName("images")
//        @Expose
//        var images: List<Image>? = null,

        @SerializedName("image")
        @Expose
        var image: String? = null,

//        @SerializedName("largeFrontImage")
//        @Expose
//        var largeFrontImage: Any? = null,
//
//        @SerializedName("mediumImage")
//        @Expose
//        var mediumImage: String? = null,
//
//        @SerializedName("thumbnailImage")
//        @Expose
//        var thumbnailImage: String? = null,
//
//        @SerializedName("largeImage")
//        @Expose
//        var largeImage: String? = null,
//
//        @SerializedName("alternateViewsImage")
//        @Expose
//        var alternateViewsImage: Any? = null,
//
//        @SerializedName("angleImage")
//        @Expose
//        var angleImage: String? = null,
//
//        @SerializedName("backViewImage")
//        @Expose
//        var backViewImage: Any? = null,
//
//        @SerializedName("energyGuideImage")
//        @Expose
//        var energyGuideImage: Any? = null,
//
//        @SerializedName("leftViewImage")
//        @Expose
//        var leftViewImage: Any? = null,
//
//        @SerializedName("accessoriesImage")
//        @Expose
//        var accessoriesImage: Any? = null,
//
//        @SerializedName("remoteControlImage")
//        @Expose
//        var remoteControlImage: Any? = null,
//
//        @SerializedName("rightViewImage")
//        @Expose
//        var rightViewImage: Any? = null,
//
//        @SerializedName("topViewImage")
//        @Expose
//        var topViewImage: Any? = null,
//
//        @SerializedName("albumTitle")
//        @Expose
//        var albumTitle: String? = null,
//
//        @SerializedName("artistName")
//        @Expose
//        var artistName: Any? = null,
//
//        @SerializedName("artistId")
//        @Expose
//        var artistId: Any? = null,
//
//        @SerializedName("originalReleaseDate")
//        @Expose
//        var originalReleaseDate: Any? = null,
//
//        @SerializedName("parentalAdvisory")
//        @Expose
//        var parentalAdvisory: Any? = null,
//
//        @SerializedName("mediaCount")
//        @Expose
//        var mediaCount: Any? = null,
//
//        @SerializedName("monoStereo")
//        @Expose
//        var monoStereo: Any? = null,
//
//        @SerializedName("studioLive")
//        @Expose
//        var studioLive: Any? = null,
//
//        @SerializedName("condition")
//        @Expose
//        var condition: String? = null,
//
//        @SerializedName("inStorePickup")
//        @Expose
//        var inStorePickup: Boolean? = null,
//
//        @SerializedName("friendsAndFamilyPickup")
//        @Expose
//        var friendsAndFamilyPickup: Boolean? = null,
//
//        @SerializedName("homeDelivery")
//        @Expose
//        var homeDelivery: Boolean? = null,
//
//        @SerializedName("quantityLimit")
//        @Expose
//        var quantityLimit: Int? = null,
//
//        @SerializedName("fulfilledBy")
//        @Expose
//        var fulfilledBy: Any? = null,
//
//        @SerializedName("members")
//        @Expose
//        var members: List<Any>? = null,
//
//        @SerializedName("bundledIn")
//        @Expose
//        var bundledIn: List<Any>? = null,
//
//        @SerializedName("albumLabel")
//        @Expose
//        var albumLabel: Any? = null,
//
//        @SerializedName("genre")
//        @Expose
//        var genre: Any? = null,

        @SerializedName("color")
        @Expose
        var color: String? = null,

//        @SerializedName("depth")
//        @Expose
//        var depth: String? = null,
//
//        @SerializedName("dollarSavings")
//        @Expose
//        var dollarSavings: Int? = null,
//
//        @SerializedName("percentSavings")
//        @Expose
//        var percentSavings: String? = null,
//
//        @SerializedName("tradeInValue")
//        @Expose
//        var tradeInValue: String? = null,
//
//        @SerializedName("height")
//        @Expose
//        var height: String? = null,
//
//        @SerializedName("orderable")
//        @Expose
//        var orderable: String? = null,
//
//        @SerializedName("weight")
//        @Expose
//        var weight: String? = null,
//
//        @SerializedName("shippingWeight")
//        @Expose
//        var shippingWeight: Double? = null,
//
//        @SerializedName("width")
//        @Expose
//        var width: String? = null,
//
//        @SerializedName("warrantyLabor")
//        @Expose
//        var warrantyLabor: String? = null,
//
//        @SerializedName("warrantyParts")
//        @Expose
//        var warrantyParts: String? = null,
//
//        @SerializedName("softwareAge")
//        @Expose
//        var softwareAge: Any? = null,
//
//        @SerializedName("softwareGrade")
//        @Expose
//        var softwareGrade: Any? = null,
//
//        @SerializedName("platform")
//        @Expose
//        var platform: Any? = null,
//
//        @SerializedName("numberOfPlayers")
//        @Expose
//        var numberOfPlayers: Any? = null,
//
//        @SerializedName("softwareNumberOfPlayers")
//        @Expose
//        var softwareNumberOfPlayers: Any? = null,
//
//        @SerializedName("esrbRating")
//        @Expose
//        var esrbRating: Any? = null,
//
//        @SerializedName("longDescription")
//        @Expose
//        var longDescription: String? = null,

//        @SerializedName("includedItemList")
//        @Expose
//        var includedItemList: List<IncludedItemList>? = null,

//        @SerializedName("marketplace")
//        @Expose
//        var marketplace: Any? = null,
//
//        @SerializedName("listingId")
//        @Expose
//        var listingId: Any? = null,
//
//        @SerializedName("sellerId")
//        @Expose
//        var sellerId: Any? = null,
//
//        @SerializedName("shippingRestrictions")
//        @Expose
//        var shippingRestrictions: Any? = null,
//
//        @SerializedName("proposition65WarningMessage")
//        @Expose
//        var proposition65WarningMessage: Any? = null,
//
//        @SerializedName("proposition65WarningType")
//        @Expose
//        var proposition65WarningType: String? = null,
//
//        @SerializedName("collection")
//        @Expose
//        var collection: String? = null,
//
//        @SerializedName("peakPowerHandling")
//        @Expose
//        var peakPowerHandling: Int? = null
) {}