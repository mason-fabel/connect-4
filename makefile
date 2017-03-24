SHELL := /bin/bash

SRC := $(wildcard src/fabe0940/ai/*.java) $(wildcard src/fabe0940/ai/util/*.java) $(wildcard src/fabe0940/ai/data/*.java) $(wildcard src/fabe0940/ai/search/*.java)
OBJ := $(addprefix obj/,$(notdir $(SRC:.java=.class)))
BIN := fabe0940.ai.Connect4

CFLAGS := -cp obj
RFLAGS := -cp obj

.PHONY : run build clean

run : build
	java $(RFLAGS) $(BIN)

build : .build

.build : $(SRC)
	javac $(CFLAGS) -d obj $(SRC)
	touch .build

clean : 
	rm -f $(OBJ)
	rm -f .build

rebuild : clean
	make
