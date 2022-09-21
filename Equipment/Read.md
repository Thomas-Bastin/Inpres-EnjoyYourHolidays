# Equipment Manangement

### Equipment Server ManagementTool:


### Equipment Client ManagementTool:


### STUff Management Protocol (STUMP):

   cmd:	  |			Detail:					|	Awnser:
-----------------------------------------------------------------------------------------------------------
* LOGIN   |  ConnectRequest						|  Yes
	  |  Param: login + password					|  No + Reason
	  |								|
-----------------------------------------------------------------------------------------------------------
* HMAT	  |  Handling Request Equipment (delivery, repair, delete)	|  Yes + action.id
	  |  Param: action + equipment + wish date			|  No + Reason
	  |  								|
-----------------------------------------------------------------------------------------------------------
* LISTCMD |  Request the List of action wished				|  Yes + list
	  |								|  No + "aucune action demandée"
	  |								|
-----------------------------------------------------------------------------------------------------------
* CHMAT	  |  Cancel Handling Request (delivery, repair, delete)		|  Yes
	  |  Param: action.id						|  No + Reason (outdated order)
	  |								|
-----------------------------------------------------------------------------------------------------------
* ASKMAT  |  Order Equipment (existing or not)				|  Yes + action.id + TimeLimit
	  |  Param: type + label + brand + price + Acessory List	|  No + Reason
	  |								|
-----------------------------------------------------------------------------------------------------------


### Equipment Type: EachType equals a new file (K_TYPE.csv)
- ID ==> Number
- Category ==> Char
- State ==>  OK, KO, DES
	 Working, Not Working, Delete