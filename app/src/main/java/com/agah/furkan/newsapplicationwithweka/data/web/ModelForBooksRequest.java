package com.agah.furkan.newsapplicationwithweka.data.web;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelForBooksRequest {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("totalItems")
    @Expose
    private Integer totalItems;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


    public class AccessInfo {

        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("viewability")
        @Expose
        private String viewability;
        @SerializedName("embeddable")
        @Expose
        private Boolean embeddable;
        @SerializedName("publicDomain")
        @Expose
        private Boolean publicDomain;
        @SerializedName("textToSpeechPermission")
        @Expose
        private String textToSpeechPermission;
        @SerializedName("epub")
        @Expose
        private Epub epub;
        @SerializedName("pdf")
        @Expose
        private Pdf pdf;
        @SerializedName("webReaderLink")
        @Expose
        private String webReaderLink;
        @SerializedName("accessViewStatus")
        @Expose
        private String accessViewStatus;
        @SerializedName("quoteSharingAllowed")
        @Expose
        private Boolean quoteSharingAllowed;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getViewability() {
            return viewability;
        }

        public void setViewability(String viewability) {
            this.viewability = viewability;
        }

        public Boolean getEmbeddable() {
            return embeddable;
        }

        public void setEmbeddable(Boolean embeddable) {
            this.embeddable = embeddable;
        }

        public Boolean getPublicDomain() {
            return publicDomain;
        }

        public void setPublicDomain(Boolean publicDomain) {
            this.publicDomain = publicDomain;
        }

        public String getTextToSpeechPermission() {
            return textToSpeechPermission;
        }

        public void setTextToSpeechPermission(String textToSpeechPermission) {
            this.textToSpeechPermission = textToSpeechPermission;
        }

        public Epub getEpub() {
            return epub;
        }

        public void setEpub(Epub epub) {
            this.epub = epub;
        }

        public Pdf getPdf() {
            return pdf;
        }

        public void setPdf(Pdf pdf) {
            this.pdf = pdf;
        }

        public String getWebReaderLink() {
            return webReaderLink;
        }

        public void setWebReaderLink(String webReaderLink) {
            this.webReaderLink = webReaderLink;
        }

        public String getAccessViewStatus() {
            return accessViewStatus;
        }

        public void setAccessViewStatus(String accessViewStatus) {
            this.accessViewStatus = accessViewStatus;
        }

        public Boolean getQuoteSharingAllowed() {
            return quoteSharingAllowed;
        }

        public void setQuoteSharingAllowed(Boolean quoteSharingAllowed) {
            this.quoteSharingAllowed = quoteSharingAllowed;
        }

    }

    public class Epub {

        @SerializedName("isAvailable")
        @Expose
        private Boolean isAvailable;
        @SerializedName("acsTokenLink")
        @Expose
        private String acsTokenLink;

        public Boolean getIsAvailable() {
            return isAvailable;
        }

        public void setIsAvailable(Boolean isAvailable) {
            this.isAvailable = isAvailable;
        }

        public String getAcsTokenLink() {
            return acsTokenLink;
        }

        public void setAcsTokenLink(String acsTokenLink) {
            this.acsTokenLink = acsTokenLink;
        }

    }

    public class ImageLinks {

        @SerializedName("smallThumbnail")
        @Expose
        private String smallThumbnail;
        @SerializedName("thumbnail")
        @Expose
        private String thumbnail;

        public String getSmallThumbnail() {
            return smallThumbnail;
        }

        public void setSmallThumbnail(String smallThumbnail) {
            this.smallThumbnail = smallThumbnail;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

    }

    public class IndustryIdentifier {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("identifier")
        @Expose
        private String identifier;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

    }

    public class Item {

        @SerializedName("kind")
        @Expose
        private String kind;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("etag")
        @Expose
        private String etag;
        @SerializedName("selfLink")
        @Expose
        private String selfLink;
        @SerializedName("volumeInfo")
        @Expose
        private VolumeInfo volumeInfo;
        @SerializedName("saleInfo")
        @Expose
        private SaleInfo saleInfo;
        @SerializedName("accessInfo")
        @Expose
        private AccessInfo accessInfo;
        @SerializedName("searchInfo")
        @Expose
        private SearchInfo searchInfo;

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEtag() {
            return etag;
        }

        public void setEtag(String etag) {
            this.etag = etag;
        }

        public String getSelfLink() {
            return selfLink;
        }

        public void setSelfLink(String selfLink) {
            this.selfLink = selfLink;
        }

        public VolumeInfo getVolumeInfo() {
            return volumeInfo;
        }

        public void setVolumeInfo(VolumeInfo volumeInfo) {
            this.volumeInfo = volumeInfo;
        }

        public SaleInfo getSaleInfo() {
            return saleInfo;
        }

        public void setSaleInfo(SaleInfo saleInfo) {
            this.saleInfo = saleInfo;
        }

        public AccessInfo getAccessInfo() {
            return accessInfo;
        }

        public void setAccessInfo(AccessInfo accessInfo) {
            this.accessInfo = accessInfo;
        }

        public SearchInfo getSearchInfo() {
            return searchInfo;
        }

        public void setSearchInfo(SearchInfo searchInfo) {
            this.searchInfo = searchInfo;
        }

    }

    public class ListPrice {

        @SerializedName("amount")
        @Expose
        private Double amount;
        @SerializedName("currencyCode")
        @Expose
        private String currencyCode;

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

    }

    public class ListPrice_ {

        @SerializedName("amountInMicros")
        @Expose
        private Integer amountInMicros;
        @SerializedName("currencyCode")
        @Expose
        private String currencyCode;

        public Integer getAmountInMicros() {
            return amountInMicros;
        }

        public void setAmountInMicros(Integer amountInMicros) {
            this.amountInMicros = amountInMicros;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

    }

    public class Offer {

        @SerializedName("finskyOfferType")
        @Expose
        private Integer finskyOfferType;
        @SerializedName("listPrice")
        @Expose
        private ListPrice_ listPrice;
        @SerializedName("retailPrice")
        @Expose
        private RetailPrice_ retailPrice;

        public Integer getFinskyOfferType() {
            return finskyOfferType;
        }

        public void setFinskyOfferType(Integer finskyOfferType) {
            this.finskyOfferType = finskyOfferType;
        }

        public ListPrice_ getListPrice() {
            return listPrice;
        }

        public void setListPrice(ListPrice_ listPrice) {
            this.listPrice = listPrice;
        }

        public RetailPrice_ getRetailPrice() {
            return retailPrice;
        }

        public void setRetailPrice(RetailPrice_ retailPrice) {
            this.retailPrice = retailPrice;
        }

    }

    public class PanelizationSummary {

        @SerializedName("containsEpubBubbles")
        @Expose
        private Boolean containsEpubBubbles;
        @SerializedName("containsImageBubbles")
        @Expose
        private Boolean containsImageBubbles;

        public Boolean getContainsEpubBubbles() {
            return containsEpubBubbles;
        }

        public void setContainsEpubBubbles(Boolean containsEpubBubbles) {
            this.containsEpubBubbles = containsEpubBubbles;
        }

        public Boolean getContainsImageBubbles() {
            return containsImageBubbles;
        }

        public void setContainsImageBubbles(Boolean containsImageBubbles) {
            this.containsImageBubbles = containsImageBubbles;
        }

    }

    public class Pdf {

        @SerializedName("isAvailable")
        @Expose
        private Boolean isAvailable;
        @SerializedName("acsTokenLink")
        @Expose
        private String acsTokenLink;

        public Boolean getIsAvailable() {
            return isAvailable;
        }

        public void setIsAvailable(Boolean isAvailable) {
            this.isAvailable = isAvailable;
        }

        public String getAcsTokenLink() {
            return acsTokenLink;
        }

        public void setAcsTokenLink(String acsTokenLink) {
            this.acsTokenLink = acsTokenLink;
        }

    }

    public class ReadingModes {

        @SerializedName("text")
        @Expose
        private Boolean text;
        @SerializedName("image")
        @Expose
        private Boolean image;

        public Boolean getText() {
            return text;
        }

        public void setText(Boolean text) {
            this.text = text;
        }

        public Boolean getImage() {
            return image;
        }

        public void setImage(Boolean image) {
            this.image = image;
        }

    }

    public class RetailPrice {

        @SerializedName("amount")
        @Expose
        private Double amount;
        @SerializedName("currencyCode")
        @Expose
        private String currencyCode;

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

    }

    public class RetailPrice_ {

        @SerializedName("amountInMicros")
        @Expose
        private Integer amountInMicros;
        @SerializedName("currencyCode")
        @Expose
        private String currencyCode;

        public Integer getAmountInMicros() {
            return amountInMicros;
        }

        public void setAmountInMicros(Integer amountInMicros) {
            this.amountInMicros = amountInMicros;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

    }

    public class SaleInfo {

        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("saleability")
        @Expose
        private String saleability;
        @SerializedName("isEbook")
        @Expose
        private Boolean isEbook;
        @SerializedName("listPrice")
        @Expose
        private ListPrice listPrice;
        @SerializedName("retailPrice")
        @Expose
        private RetailPrice retailPrice;
        @SerializedName("buyLink")
        @Expose
        private String buyLink;
        @SerializedName("offers")
        @Expose
        private List<Offer> offers = null;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getSaleability() {
            return saleability;
        }

        public void setSaleability(String saleability) {
            this.saleability = saleability;
        }

        public Boolean getIsEbook() {
            return isEbook;
        }

        public void setIsEbook(Boolean isEbook) {
            this.isEbook = isEbook;
        }

        public ListPrice getListPrice() {
            return listPrice;
        }

        public void setListPrice(ListPrice listPrice) {
            this.listPrice = listPrice;
        }

        public RetailPrice getRetailPrice() {
            return retailPrice;
        }

        public void setRetailPrice(RetailPrice retailPrice) {
            this.retailPrice = retailPrice;
        }

        public String getBuyLink() {
            return buyLink;
        }

        public void setBuyLink(String buyLink) {
            this.buyLink = buyLink;
        }

        public List<Offer> getOffers() {
            return offers;
        }

        public void setOffers(List<Offer> offers) {
            this.offers = offers;
        }

    }

    public class SearchInfo {

        @SerializedName("textSnippet")
        @Expose
        private String textSnippet;

        public String getTextSnippet() {
            return textSnippet;
        }

        public void setTextSnippet(String textSnippet) {
            this.textSnippet = textSnippet;
        }

    }

    public class VolumeInfo {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("subtitle")
        @Expose
        private String subtitle;
        @SerializedName("authors")
        @Expose
        private List<String> authors = null;
        @SerializedName("publishedDate")
        @Expose
        private String publishedDate;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("industryIdentifiers")
        @Expose
        private List<IndustryIdentifier> industryIdentifiers = null;
        @SerializedName("readingModes")
        @Expose
        private ReadingModes readingModes;
        @SerializedName("pageCount")
        @Expose
        private Integer pageCount;
        @SerializedName("printType")
        @Expose
        private String printType;
        @SerializedName("maturityRating")
        @Expose
        private String maturityRating;
        @SerializedName("allowAnonLogging")
        @Expose
        private Boolean allowAnonLogging;
        @SerializedName("contentVersion")
        @Expose
        private String contentVersion;
        @SerializedName("panelizationSummary")
        @Expose
        private PanelizationSummary panelizationSummary;
        @SerializedName("language")
        @Expose
        private String language;
        @SerializedName("previewLink")
        @Expose
        private String previewLink;
        @SerializedName("infoLink")
        @Expose
        private String infoLink;
        @SerializedName("canonicalVolumeLink")
        @Expose
        private String canonicalVolumeLink;
        @SerializedName("publisher")
        @Expose
        private String publisher;
        @SerializedName("categories")
        @Expose
        private List<String> categories = null;
        @SerializedName("imageLinks")
        @Expose
        private ImageLinks imageLinks;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public List<String> getAuthors() {
            return authors;
        }

        public void setAuthors(List<String> authors) {
            this.authors = authors;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public void setPublishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<IndustryIdentifier> getIndustryIdentifiers() {
            return industryIdentifiers;
        }

        public void setIndustryIdentifiers(List<IndustryIdentifier> industryIdentifiers) {
            this.industryIdentifiers = industryIdentifiers;
        }

        public ReadingModes getReadingModes() {
            return readingModes;
        }

        public void setReadingModes(ReadingModes readingModes) {
            this.readingModes = readingModes;
        }

        public Integer getPageCount() {
            return pageCount;
        }

        public void setPageCount(Integer pageCount) {
            this.pageCount = pageCount;
        }

        public String getPrintType() {
            return printType;
        }

        public void setPrintType(String printType) {
            this.printType = printType;
        }

        public String getMaturityRating() {
            return maturityRating;
        }

        public void setMaturityRating(String maturityRating) {
            this.maturityRating = maturityRating;
        }

        public Boolean getAllowAnonLogging() {
            return allowAnonLogging;
        }

        public void setAllowAnonLogging(Boolean allowAnonLogging) {
            this.allowAnonLogging = allowAnonLogging;
        }

        public String getContentVersion() {
            return contentVersion;
        }

        public void setContentVersion(String contentVersion) {
            this.contentVersion = contentVersion;
        }

        public PanelizationSummary getPanelizationSummary() {
            return panelizationSummary;
        }

        public void setPanelizationSummary(PanelizationSummary panelizationSummary) {
            this.panelizationSummary = panelizationSummary;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getPreviewLink() {
            return previewLink;
        }

        public void setPreviewLink(String previewLink) {
            this.previewLink = previewLink;
        }

        public String getInfoLink() {
            return infoLink;
        }

        public void setInfoLink(String infoLink) {
            this.infoLink = infoLink;
        }

        public String getCanonicalVolumeLink() {
            return canonicalVolumeLink;
        }

        public void setCanonicalVolumeLink(String canonicalVolumeLink) {
            this.canonicalVolumeLink = canonicalVolumeLink;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public List<String> getCategories() {
            return categories;
        }

        public void setCategories(List<String> categories) {
            this.categories = categories;
        }

        public ImageLinks getImageLinks() {
            return imageLinks;
        }

        public void setImageLinks(ImageLinks imageLinks) {
            this.imageLinks = imageLinks;
        }

    }
}