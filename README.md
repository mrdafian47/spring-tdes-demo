# Spring 3DES Demo

Spring Demo project for learning encrypt & decrypt string with algorithm 3DES.

---

## Testing with PostMan

### Encrypt endpoint

URL: http://localhost:9001/3des/encrypt

Method: POST

Request Body: 
```json
{
	"message": "I want to fly high"
}
```

Example Response

Response Body:
```json
{
	"data": "GOKECok7aXZI6+1/j3Px2EABHw3zGAW2"
}
```

### Decrypt endpoint

URL: http://localhost:9001/3des/decrypt

Method: POST

Request Body:
```json
{
	"message": "GOKECok7aXZI6+1/j3Px2EABHw3zGAW2"
}
```

Example Response

Response Body:
```json
{
	"data": "I want to fly high"
}
```

---

Learning by this link:
https://www.baeldung.com/java-3des