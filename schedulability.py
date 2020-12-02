import math

data = [
    (40, 20),
    (280, 28),
    (280, 28),
    (280, 28),
    (280, 28),
    (280, 28),
]

maxval = maxidx = -1

for i in range(len(data)):
    if data[i][0] > maxval:
        maxval = data[i][0]
        maxidx = i

r = sum(list(map(lambda x: x[1], data)))

while True:
    rp = 0
    for i in range(len(data)):
        if i == maxidx:
            rp += data[i][1]
        else:
            rp += math.ceil(r / data[i][0]) * data[i][1]
    if r == rp:
        break
    r = rp
    print(r)
    if r > data[maxidx][0]:
        break

print(r, rp, r <= data[maxidx][0])