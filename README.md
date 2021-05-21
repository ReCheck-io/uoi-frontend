# UOI Frontend  
  
UOI Frontend is a web application accessed via web browser which makes possible the creation, updating and searching of the Unique object identifier (UOI) through service provider called UOI Backend (which implements an API). Frontend and Backend exchange messages in a request–response messaging pattern.  
  
This repository contains the source code of the UOI Frontend (no UOI backend API code here)  
  
UOI Frontend is currently hosted on [https://uoi-frontend.recheck.io/](https://uoi-frontend.recheck.io/)  
  
## Data model of UOI
- Country code: should answer which country is a resident of the building unit. The format must be "ISO 3166-1 alpha-2"  
- Level: It is the level in the topology of the UOI system. As of now there are the following levels: _BUILDING\_BLOCK, BUILDING, UNIT, ROOM, WALL, ELEMENT, PRODUCT\_SAMPLE_  
- UOI: The UOI is created by having the country&#39;s acronym in capital letters + the uuid4 (universally unique identifier). Ex: NL.dfb2d5f4-89e3-43b0-840d-7c1cc57aa014  
- Properties: Contains metadata for concrete UOI. It is list of <key, value> pair (map).  
 Example properties:  
   * length: 0,  
   * height: 0,  
   * width: 0,  
   * materials: null,  
   * tenant: null,  
   * address: null,  
   * longitude: 0,  
   * latitude: 0,  
   * resources: null,  
   * BAG: "123123213",  
   * Company Owner: "Hello Inc",  
   * VAT: "12312312321  
  
## User interface Components
See user interface Components [here](https://github.com/ReCheck-io/uoi-frontend/blob/main/README%20-%20User%20interface%20components.md)
  
## **Use cases**
See use cases [here](https://github.com/ReCheck-io/uoi-frontend/blob/main/README%20-%20Use%20cases.md)