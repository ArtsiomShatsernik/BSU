var randNumber = -1
function getRandomNumber(max) {
		var newRandNumber = randNumber;
		while (newRandNumber === randNumber) {
			newRandNumber = Math.floor(Math.random() * max)
		}		
		randNumber = newRandNumber
		return randNumber
}
function getRandomTimeString(max) {
	var num = getRandomNumber(max)
	if (num < 10) {
		return '0' + num.toString()
	} else {
		return num.toString()
	}
}

var today = new Date()
var authors = ['Шатерник Артём', 'Не Шатерник Артём']
var cities = ['Минск', 'Брест', 'Гродно', 'Гомель', 'Витебск', 'Могилев', 'Слоним', 'Туров', 'Полоцк'];
var tickets = []
for (var i = 0; i < 20; i++) {
	var ticketTemp = {
		id: i,
		description: 'Описание возможного билета',
		createdAt: new Date(2023, 5, getRandomNumber(30)),
		author: authors[getRandomNumber(authors.length)],
		route: {
			start: cities[getRandomNumber(cities.length)],
			finish: cities[getRandomNumber(cities.length)]
		},	
		dateAndTime: {
			date: getRandomTimeString(30) + '.05.2023',
			time: getRandomTimeString(24) + ':' + getRandomTimeString(60)
		}
	}
	tickets.push(ticketTemp)
}

class TicketsClass {
	constructor(Objs) {
		this.Objs = Objs
	}
	showObjs(id = -1) {
		if (id != -1 && id >= 0 && id < this.Objs.length) {
			console.log(this.Objs[id])
		} else {
			console.log(this.Objs)
		}
	}
	getObjs(skip = 0, top = this.Objs.length, filterVal = {} ) {
		var temp = []
		for (i = 0; i < top; i++) {
			temp.push(this.Objs[skip + i])
		}
		var key = Object.keys(filterVal)
		temp = temp.filter(obj => obj[key] === filterVal[key])
		
		return temp.sort((a, b) => new Date(a.createdAt) - new Date(b.createdAt))
	}
	getObj(id) {
		return this.Objs[id]
	}
	validateObj(obj) {
		var keyProperties = ['id', 'description', 'createdAt', 'author']
		return keyProperties.every(property => property in obj)
	}
	addObj(obj) {
		if (this.validateObj(obj)) {
			this.Objs.push(obj)
			return true
		} else {
			return false
		}
	}
	editObj(id, newValues) {
		var restrictions = ['id', 'author', 'createdAt']
		if (!(restrictions.includes(Object.keys(newValues)[0]))) {
			var findObject = this.Objs.find(obj => obj.id === id)
			if (findObject) {
				Object.assign(findObject, newValues);
			}
			return true
		} else {
			return false
		}
	}
	removeObj(id) {
		if (this.Objs = this.Objs.filter(obj => obj.id !== id)) {
			return true
		} else {
			return false
		}
	}
	makeMap() {
		var collection = new Map()
		for(var obj of this.Objs) {
			collection.set(obj.id, obj)
		}
		return collection
	}
}

Tickets1 = new TicketsClass(tickets)
// Проверка getObjs()
var t1 = Tickets1.getObjs(0, 20, {route : {start : 'Минск'}})
for (var value of t1) {
	console.log(value)
}
// Проверка getObj()
var t2 = Tickets1.getObj(5)
console.log(t2)
// Проверка validateObj()
var testObj = {
	id: 101,
	description: 'Тестовый объект',
	createdAt:  new Date(2023, 5, 1),
	author: 'Аноним'
}
console.log(Tickets1.validateObj(testObj))
// Проверка addObj()
console.log(Tickets1.addObj(testObj))
Tickets1.showObjs()
// Проверка editObj()
console.log(Tickets1.editObj(0, {route: {start: "новый", finish: "новый"}}))
Tickets1.showObjs(0)
// Проверка removeObj()
Tickets1.removeObj(0)
Tickets1.showObjs()
// Проверка makeMap()
var map = Tickets1.makeMap()
console.log(map)
