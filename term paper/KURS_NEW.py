# Подключение необходимых библиотек
import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as sps
import math
import enum


# Класс реализующий все модели
class ValdTestNormal:
    # Список вариантов теста
    class __TestType(enum.Enum):
        standard = 0
        discrete = 1
        discrete_v2 = 2

    # Конструктор класса
    def __init__(self, mean: list, alpha, beta, var=1, sample=None):
        self.result = None
        self.gen_sample = None
        self.mean = mean
        self.var = var
        self.alpha = alpha
        self.beta = beta
        self.sample = sample
        self.__cur_line = None
        self.test_type = ValdTestNormal.__TestType.standard
        self.__calculate_thresholds()
        self.discrete_space = []
        plt.figure(figsize=(10, 5))

    # Метод вычисляющий пороги
    def __calculate_thresholds(self):
        self.A = math.log((1 - self.beta) / self.alpha)
        self.B = math.log(self.beta / (1 - self.alpha))

    # Дискретизация пространства
    def __calculate_discrete_space(self, m):
        h = (self.A - self.B) / m
        self.discrete_space.append(self.A)
        for i in range(m - 1):
            line = self.B + (i + 1) * h
            self.discrete_space.append(line)
        self.discrete_space.append(self.B)

    # Поиск ближайшей линии
    def __find_line(self, value, list):
        if self.test_type == ValdTestNormal.__TestType.discrete or self.__cur_line is None:
            self.__cur_line = min(list, key=lambda x: abs(x - value))
        elif self.test_type == ValdTestNormal.__TestType.discrete_v2:
            line_temp = min(list, key=lambda x: abs(x - value))
            if line_temp == self.__cur_line and line_temp != self.A and line_temp != self.B:
                temp_id = list.index(line_temp)
                adjacent_val = [list[temp_id + 1], list[temp_id - 1]]
                self.__cur_line = min(adjacent_val, key=lambda x: abs(x - value))
            else:
                self.__cur_line = line_temp
        return self.__cur_line

    # Генерация выборок
    def __generate_sample(self, true_index=1, size=30000):
        return np.random.normal(self.mean[true_index], self.var, size)

    # Метод, реализующий тест
    def __test(self, sample):
        # Считаем количество итераций
        n = 0
        likelihood = 0
        values = np.array([])
        while True:
            x = sample[n]
            n += 1
            # Вычисляем отношение правдоподобия
            elem = (self.mean[1] - self.mean[0]) * (x - (self.mean[1] + self.mean[0]) / 2)
            likelihood += elem
            if self.test_type != ValdTestNormal.__TestType.standard:
                likelihood = self.__find_line(likelihood, self.discrete_space)
            values = np.append(values, likelihood)
            if likelihood >= self.A:
                self.result = 1
                self.n = n
                self.values = values
                return 1, n
            elif likelihood <= self.B:
                self.result = 0
                self.n = n
                self.values = values
                return 0, n

    # Матрица переходов ЦМ
    def __build_matrix(self, m):
        delta = abs(self.mean[0] - self.mean[1])
        h = (self.A - self.B) / m
        p = np.zeros((m + 2, m + 2))
        for i in range(m + 2):
            for j in range(m + 2):
                if i == 0 or i == 1:
                    if i == j:
                        p[i][j] = 1
                    else:
                        p[i][j] = 0
                elif j == 0 and i != 0 and i != 1:
                    p[i][j] = sps.norm.cdf(delta / 2 + h / (2 * delta) + h * (1 - i) / delta) - 0.5
                elif j == 1 and i != 0 and i != 1:
                    p[i][j] = sps.norm.cdf(delta / 2 - h / (2 * delta) + (self.A - self.B + 2 - i) / delta) - 0.5
                else:
                    p[i][j] = sps.norm.cdf(delta / 2 + (h * (2 * (j - i) + 1)) / (2 * delta)) \
                              - sps.norm.cdf(delta / 2 + (h * (2 * (j - i) - 1)) / (2 * delta))
        return p

    # Начальные состояния ЦМ
    def __build_initial_state(self, m):
        delta = abs(self.mean[0] - self.mean[1])
        h = (self.A - self.B) / m
        state = np.zeros(m)
        for i in range(2, m + 2):
            state[i - 2] = sps.norm.cdf(delta / 2 + (self.B + (i - 1) * h) / delta) \
                           - sps.norm.cdf(delta / 2 + (self.B + (i - 2) * h) / delta)
        return state

    # Аппроксимация цепью Маркова
    def markov_chain_test(self, m):
        # При верной гипотезе H0
        p = self.__build_matrix(m)
        state = self.__build_initial_state(m)
        I = p[0:2, 0:2]
        R = p[2:m + 2, 0:2]
        Q = p[2:m + 2, 2:m + 3]
        S = np.eye(m) - Q
        inv_S = np.linalg.inv(S)
        temp = np.dot(state, inv_S)
        t = sum(temp) + 1
        return t
        # B = np.dot(inv_S, R)
        # # alpha = np.dot(state, B[0:m, 1:2])
        # alpha = np.dot(state, B)
        # print(f"a: {alpha}")

    # Запуск теста по выборке
    def start_test_sample(self, sample=None):
        self.test_type = ValdTestNormal.__TestType.standard
        if sample is not None:
            self.sample = sample
        if self.sample is None:
            self.sample = self.__generate_sample()
        return self.__test(self.sample)

    # Запуск теста с генерацией выборки
    def start_test_gen(self, true_index=0):
        self.test_type = ValdTestNormal.__TestType.standard
        sample = self.__generate_sample(true_index)
        return self.__test(sample)

    # Запуск первого дискретного теста по выборке
    def start_discrete_test_sample(self, m, sample=None):
        self.test_type = ValdTestNormal.__TestType.discrete
        if sample is not None:
            self.sample = sample
        if self.sample is None:
            self.sample = self.__generate_sample()
        self.__calculate_discrete_space(m)
        return self.__test(self.sample)

    # Запуск первого дискретного теста c генерацией выборки
    def start_discrete_test_gen(self, m, true_index=0):
        self.test_type = ValdTestNormal.__TestType.discrete
        self.__calculate_discrete_space(m)
        sample = self.__generate_sample(true_index)
        return self.__test(sample)

    # Запуск второго дискретного теста по выборке
    def start_discrete_v2_test_sample(self, m, sample=None):
        self.test_type = ValdTestNormal.__TestType.discrete_v2
        if sample is not None:
            self.sample = sample
        if self.sample is None:
            self.sample = self.__generate_sample()
        self.__calculate_discrete_space(m)
        return self.__test(self.sample)

    # Запуск второго дискретного теста c генерацией выборки
    def start_discrete_v2_test_gen(self, m, true_index=0):
        self.test_type = ValdTestNormal.__TestType.discrete_v2
        self.__calculate_discrete_space(m)
        sample = self.__generate_sample(true_index)
        return self.__test(sample)

    # Подсчёт априорного числа итераций
    def get_apriori_num_of_iterations(self):
        return 2 / (abs(self.mean[0] - self.mean[1]) ** 2) * (1 - 2 * self.alpha) * math.log(
            (1 - self.alpha) / self.alpha)

    # Построение таблицы с разными значениями параметров
    def get_stat_table(self, delta, m, L=1000):
        standard_t = []
        standard_alpha = []
        discrete_t = {}
        discrete_v2_t = {}
        discrete_alpha = {}
        discrete_v2_alpha = {}
        for key in m:
            discrete_t[key] = []
            discrete_v2_t[key] = []
            discrete_alpha[key] = []
            discrete_v2_alpha[key] = []
        for d in delta:
            self.mean = d
            iterations = 0
            alphas = 0
            for i in range(L):
                self.test_type = ValdTestNormal.__TestType.standard
                hyp_res, it_res = self.__test(self.__generate_sample(true_index=0))
                iterations += it_res
                if hyp_res != 0:
                    alphas += 1
            standard_t.append(iterations / L)
            standard_alpha.append(alphas / L)
        print("Standard_t:\n", standard_t)
        print("Standard_alpha:\n", standard_alpha)
        self.test_type = ValdTestNormal.__TestType.discrete
        for j in m:
            self.__calculate_discrete_space(j)
            for d in delta:
                self.mean = d
                iterations = 0
                alphas = 0
                for i in range(L):
                    hyp_res, it_res = self.__test(self.__generate_sample(true_index=0))
                    iterations += it_res
                    if hyp_res != 0:
                        alphas += 1
                discrete_t[j].append(iterations / L)
                discrete_alpha[j].append(alphas / L)
                print("Discrete_t\n", discrete_t)
                print("Discrete_alpha\n", discrete_alpha)
        self.test_type = ValdTestNormal.__TestType.discrete_v2
        for j in m:
            self.__calculate_discrete_space(j)
            for d in delta:
                self.mean = d
                iterations = 0
                alphas = 0
                for i in range(L):
                    hyp_res, it_res = self.__test(self.__generate_sample(true_index=0))
                    iterations += it_res
                    if hyp_res != 0:
                        alphas += 1
                discrete_v2_t[j].append(iterations / L)
                discrete_v2_alpha[j].append(alphas / L)
                print("Discrete_v2_t\n", discrete_v2_t)
                print("Discrete_v2_alpha\n", discrete_v2_alpha)
                iterations = 0
                alphas = 0
                self.__cur_line = None
        return standard_t, standard_alpha, discrete_t, discrete_alpha, discrete_v2_t, discrete_v2_alpha

    # Подсчёт характеристик теста
    def calculate_stats(self, size, m=10, markov_chain=False):
        if markov_chain:
            markov_res = self.markov_chain_test(m)
            print(f"Аппроксимация числа итераций ЦМ: {markov_res}")
        print(f"Проведено тестов: {size}")
        print(f"Число разбиений: {m}")
        print(f"Расстояние между параметрами: {abs(self.mean[0] - self.mean[1])}")
        print(f"alpha: {self.alpha}\nbeta: {self.beta}\n")
        self.__calculate_discrete_space(m)
        n = len(ValdTestNormal.__TestType)
        iterations = [[0] * n, [0] * n]
        errors = [[0] * n, [0] * n]
        for h in range(2):
            for test_type in ValdTestNormal.__TestType:
                self.test_type = test_type
                for i in range(size):
                    hyp_res, it_res = self.__test(self.__generate_sample(true_index=h))
                    iterations[h][test_type.value] += it_res
                    if hyp_res != h:
                        errors[h][test_type.value] += 1
                print(f"Вид теста: {test_type.name}\nСреднее число итераций: {iterations[h][test_type.value] / size}\n"
                      f"Вероятность ошибки при гипотезе {h}: {errors[h][test_type.value] / size}\n")

    # Добавление результата к графику
    def add_result(self, show=False):
        if self.result is None:
            raise Exception("No result found")
        x = np.arange(1, self.n + 1, 1)
        if self.n == 1:
            plt.plot(x, self.values, '.')
        else:
            plt.ylim(self.B - 0.5, self.A + 0.5)
            plt.plot(x, self.values)
        title_text = f"Визуализация теста при значениях параметров {self.mean[0]} и {self.mean[1]}"
        # plt.title(title_text)
        plt.axhline(y=self.A, color='red', linestyle='--')
        plt.axhline(y=self.B, color='red', linestyle='--')
        plt.grid()
        if show:
            self.show_graph()
        return self

    # Добавление дискретного пространства к графику
    def add_discrete_space(self):
        if not self.discrete_space:
            raise Exception("No discrete space found")
        for i in range(len(self.discrete_space)):
            plt.axhline(self.discrete_space[i], color='green', alpha=0.5, linestyle="--")
        return self

    # Вывод графика
    @staticmethod
    def show_graph():
        plt.xlabel("Число итераций")
        plt.ylabel("Значение теста")
        plt.show()
        plt.clf()
        plt.figure(figsize=(10, 4))


means = [0, 0.05]
alpha, beta = 0.05, 0.05
m = 200
test = ValdTestNormal(means, alpha, beta, var=1)
# results1 = test.start_test_sample()
# test.add_result().show_graph()
# results2 = test.start_discrete_test_sample(m)
# test.add_result().show_graph()
# results3 = test.start_discrete_v2_test_sample(m)
# test.add_result().show_graph()
# print(f"Гипотеза: H {results1[0]}\nЧисло итераций: {results1[1]}")
# print(f"Гипотеза: H {results2[0]}\nЧисло итераций: {results2[1]}")
# print(f"Гипотеза: H {results3[0]}\nЧисло итераций: {results3[1]}")
print("Априорное число итераций:", test.get_apriori_num_of_iterations())
markov_res = test.markov_chain_test(m)
print(f"Результат аппроксимации цепью Маркова: {markov_res} итераций")
# test.calculate_stats(10000, m)
# delta = [[0, 2], [0, 1], [0, 0.5], [0, 0.2]]
# m = [200, 100, 50, 20]
# res = test.get_stat_table(delta=delta, m=m, L=10000)
# print(res)
