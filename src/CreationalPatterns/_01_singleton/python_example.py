class Test:
    def __init__(self):
        self.animal = ''
        self.count = 0

    def setAnimal(self, animal):
        self.animal = animal
        return self.animal

    def setCount(self, count):
        self.count += count
        return self.count

    def print(self):
        print('동물종: ' + self.animal + ' 입고 수량: ' +  self.count)

# ref: 파이썬 싱글톤 패턴 https://wikidocs.net/69361
