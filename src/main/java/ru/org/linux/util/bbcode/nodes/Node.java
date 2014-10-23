/*
 * Copyright 1998-2014 Linux.org.ru
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

/*
 * Copyright (c) 2005-2006, Luke Plant
 * All rights reserved.
 * E-mail: <L.Plant.98@cantab.net>
 * Web: http://lukeplant.me.uk/
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *
 *      * Redistributions in binary form must reproduce the above
 *        copyright notice, this list of conditions and the following
 *        disclaimer in the documentation and/or other materials provided
 *        with the distribution.
 *
 *      * The name of Luke Plant may not be used to endorse or promote
 *        products derived from this software without specific prior
 *        written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Rewrite with Java language and modified for lorsource by Ildar Hizbulin 2011
 * E-mail: <hizel@vyborg.ru>
 */

package ru.org.linux.util.bbcode.nodes;

import ru.org.linux.util.bbcode.ParserParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * Базовый класс для узлов дерева разбора LORCODE
 */
abstract public class Node {
  protected final Node parent;
  private final List<Node> children;
  protected String parameter;
  protected final ParserParameters parserParameters;

  public Node(ParserParameters parserParameters) {
    this.parserParameters = parserParameters;
    children = new ArrayList<>();
    parent = null;
  }

  public Node(Node parent, ParserParameters parserParameters) {
    this.parserParameters = parserParameters;
    this.parent = parent;
    children = new ArrayList<>();
  }

  public Node getParent() {
    return parent;
  }

  public boolean allows(String tagname) {
    return false;
  }

  public boolean prohibited(String tagname) {
    return false;
  }

  public int lengthChildren() {
    return children.size();
  }

  public List<Node> getChildren() {
    return children;
  }

  public Node lastChildren() {
    return children.get(children.size() - 1);
  }

  public boolean isParameter() {
    return (parameter != null) && (!parameter.isEmpty());
  }

  public String getParameter() {
    return parameter;
  }

  public void setParameter(String parameter) {
    this.parameter = parameter;
  }

  abstract public String renderXHtml();

  abstract public String renderBBCode();

  public String renderChildrenXHtml() {
    StringBuilder stringBuilder = new StringBuilder();
    for (Node child : children) {
      stringBuilder.append(child.renderXHtml());
    }
    return stringBuilder.toString();
  }

  public String renderChildrenBBCode() {
    StringBuilder stringBuilder = new StringBuilder();
    for (Node child : children) {
      stringBuilder.append(child.renderBBCode());
    }
    return stringBuilder.toString();
  }

  public String renderOg() {
    return "";
  }

  public final String renderChildrenOg() {
    StringBuilder stringBuilder = new StringBuilder();
    for(Node child : children) {
      stringBuilder.append(child.renderOg());
      stringBuilder.append(' ');
    }
    return stringBuilder.toString().trim();
  }
}
