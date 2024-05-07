export interface SMTEvents {
    events: SMTEvent[];
}

export interface SMTEvent {
    id:               number;
    section:          Section;
    category:         string;
    categoryOrder:    number;
    eventOrder:       number;
    name:             string;
    type:             Type;
    modifiedDate:     Date;
    isVisibleToUser:  number;
    isSpecialPayload: number;
    payload:          Payload;
}

export interface Payload {
    tc?:              number;
    image_id?:        number;
    hostname?:        string;
    subpath1?:        string;
    subpath2?:        string;
    screenname?:      string;
    url?:             string;
    subpath?:         string;
    isAdmin?:         boolean;
    isNull?:          null;
    product?:         Item[] | Item;
    similar_images?:  string[];
    items?:           Item[];
    cartTotal?:       number;
    deliveryCharges?: number;
    amountPayable?:   number;
    prid?:            number;
    name?:            string;
    price?:           number;
    currency?:        Currency;
    company?:         Category;
    color?:           Color;
    category?:        Category;
    quantity?:        number;
    description?:     string;
    search_query?:    Category;
    NaMe?:            string;
    aGe?:             number;
    suBJects?:        SuBJects;
    DatE?:            Date;
    title?:           string;
    deeplinkURL?:     string;
    mediaURL1?:       string;
    mediaURL2?:       string;
    mediaURL3?:       string;
    mediaURL4?:       string;
    mediaURL5?:       string;
    filters?:         Filter[];
    subtitle?:        string;
}

export enum Category {
    Apple = "Apple",
    Electronics = "Electronics",
}

export enum Color {
    SpaceGray = "Space Gray",
}

export enum Currency {
    Empty = "₹",
}

export interface Filter {
    eventConfig:              EventConfig;
    prid?:                    number;
    name?:                    string;
    prqt?:                    number;
    price?:                   number;
    itemprice?:               number;
    product?:                 ProductElement[] | PurpleProduct;
    country?:                 Country[];
    countrygdp?:              number[];
    countrycode?:             number[];
    NAME?:                    string;
    Name?:                    string;
    COUNTRYCODE?:             number[];
    CountryCode?:             number[];
    id?:                      number;
    title?:                   Title;
    message?:                 Message;
    bg_image_url?:            BgImageURL;
    label_action_button1?:    LabelActionButton1;
    deeplink_action_button1?: DeeplinkActionButton1;
    label_action_button2?:    LabelActionButton2;
    deeplink_action_button2?: DeeplinkActionButton2;
    pnative_image_url?:       PnativeImageURL;
    pnative_deeplink?:        PnativeDeeplink;
    pnative_url?:             string;
    position?:                Position;
}

export enum BgImageURL {
    BlankBgImage = "blank-bg-image",
    HTTPImageURL = "http-image-URL",
    NoKeyBgImage = "no-key-bg-image",
    SDKHTTPImageURL = "SDK$$-http-image-URL",
}

export enum Country {
    Aus = "aus",
    China = "china",
    CountryINDIA = "INDIA",
    CountryUS = "US",
    InappInteger = "Inapp Integer",
    India = "india",
    Uk = "uk",
    Us = "us",
}

export enum DeeplinkActionButton1 {
    BlankAb1Dp1 = "blank-ab1-dp1",
    NoKeyAb1Dp1 = "no-key-ab1-dp1",
    SDKHTTPSWWWGoogleCOM = "SDK$$-https://www.google.com",
    SDKSmartechCustom = "SDK$$-smartech://custom",
    SmartechCustom = "smartech://custom",
}

export enum DeeplinkActionButton2 {
    BlankAb2Dp2 = "blank-ab2-dp2",
    NoKeyAb2Dp2 = "no-key-ab2-dp2",
    SDKHTTPSWWWGoogleCOM = "SDK$$-https://www.google.com",
    SDKSmartechCustom = "SDK$$-smartech://custom",
    SmartechCustom = "smartech://custom",
}

export interface EventConfig {
    success:     number;
    bgColor:     BgColor;
    successText: SuccessText;
}

export enum BgColor {
    C0392B = "#C0392B",
    The27Ae60 = "#27AE60",
}

export enum SuccessText {
    Blank = "Blank",
    C = "C",
    F = "F",
    H = "H",
    Hi = "HI",
    I = "I",
    No = "No",
    NoKey = "No Key",
    SDK = "SDK",
    SuccessTextYes = "yes",
    Yes = "Yes",
}

export enum LabelActionButton1 {
    BlankAb1 = "blank-ab1",
    ButtonOne = "Button One",
    NoKeyAb1 = "no-key-ab1",
    SDKButtonOne = "SDK$$-Button One",
}

export enum LabelActionButton2 {
    BlankAb2 = "blank-ab2",
    ButtonTwo = "Button Two",
    NoKeyAb2 = "no-key-ab2",
    SDKButtonTwo = "SDK$$-Button Two",
}

export enum Message {
    BlankMsg = "blank-msg",
    NoKeyMsg = "no-key-msg",
    SDKThisMsg = "SDK$$-This-msg",
    ThisMsg = "This-msg",
}

export enum PnativeDeeplink {
    BlankPnativeDP = "blank-pnative-dp",
    NoKeyPnativeDP = "no-key-pnative-dp",
    SDKSmartechCustom = "SDK$$-smartech://custom",
    SmartechCustom = "smartech://custom",
}

export enum PnativeImageURL {
    BlankPnativeImage = "blank-pnative-image",
    HTTPImageURL = "http-image-URL",
    NoKeyPnativeImage = "no-key-pnative-image",
    SDKHTTPImageURL = "SDK$$-http-image-URL",
}

export enum Position {
    C = "c",
    F = "f",
    H = "h",
    Hi = "hi",
    I = "i",
}

export interface ProductElement {
    prqt:   number;
    name:   Country;
    prid:   number;
    price?: number;
    PRICE?: number;
    Price?: number;
}

export interface PurpleProduct {
    prqt:  number;
    name:  string;
    prid?: number;
    price: number;
    PRID?: number;
    Prid?: number;
}

export enum Title {
    BlankTitle = "blank-title",
    NoKeyTitle = "no-key-title",
    SDKThisTitle = "SDK$$-This-Title",
    ThisTitle = "This-Title",
}

export interface Item {
    prid?:        number;
    name:         string;
    price?:       number;
    currency?:    Currency;
    imagename?:   string;
    url?:         string;
    company?:     Category;
    color?:       Color;
    category:     Category;
    quantity?:    number;
    description?: string;
    brand?:       Category;
    sortByPrice?: number;
}

export interface SuBJects {
    rHyMeS:    RHyMeS;
    Games:     Games;
    Crayoning: Crayoning;
    GamesList: string[];
}

export interface Crayoning {
    GamesList: string[];
    Test1:     number;
    test2:     number;
    TEST3:     number;
}

export interface Games {
    TEST1: number;
    test2: number;
    TEST3: RHyMeS;
}

export interface RHyMeS {
    TEST1: number;
    test2: number;
    Test3: number;
}

export enum Section {
    Events = "Events",
    InAppMessages = "In App Messages",
    PushNotifications = "Push Notifications",
    SystemEvents = "System Events",
}

export enum Type {
    System = "System",
}
