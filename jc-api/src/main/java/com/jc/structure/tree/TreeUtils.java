package com.jc.structure.tree;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class TreeUtils {

	private static final Logger log = Logger.getLogger(TreeUtils.class);

	/**
	 * Recursive tree traversal
	 */
	public static <T> List<T> extractObjects(TreeNode<T> node) {
		List<T> result = new ArrayList<>();
		result.add(node.getObject());

		Iterator<TreeNode> p = node.childrenIterator();
		Iterator<TreeNode> prev = null;
		TreeNode pitem;
		while (p.hasNext()) {
			pitem = p.next();
			result.add((T) pitem.getObject());
			if (pitem.hasChildren()) {
				prev = p;
				p = pitem.childrenIterator();
			} else if (prev != null && !p.hasNext()) {
				p = prev;
			}
		}
		return result;
	}

	public static <T> TreeNode<T> toNode(T o) {
		return new TreeNode<>(o);
	}

	private static <T extends ITreeObject> void validateTree(List<T> olist) {
		for (T p : olist) {
			int pid = p.getParentId();
			if (pid < 1) continue;
			boolean found = false;
			for (T q : olist) {
				if (q.getId() == pid) {
					found = true;
					break;
				}
			}

			if (!found) throw new IllegalStateException("validateTree: invalid tree list=" + olist);
		}
	}

	/**
	 * Ehitab hierarhiliseks
	 */
	public static <T extends ITreeObject> TreeNode<T> toTree(List<T> olist) {
		if (olist.size() < 1) throw new IllegalArgumentException("toTree: olist.size < 1");
		validateTree(olist);
		List<T> list = new ArrayList<>(olist);//kuna me hakkame listist kustutama

		log.debug("toTree: list.size=" + list.size());

		TreeNode<T> root = null;
		boolean hasRoot = false;
		Iterator<T> p = list.iterator();
		T pitem;
		while (p.hasNext()) {
			pitem = p.next();
			if (pitem.getParentId() < 1) {
				if (hasRoot) throw new IllegalStateException("toTree: kahte ruutu ei tohi olla");
				hasRoot = true;
				root = toNode(pitem);
				p.remove();
			}
		}

		if (root == null) throw new IllegalStateException("toTree: ei leidnud ruutu");

		//sets up root children
		p = list.iterator();
		while (p.hasNext()) {
			pitem = p.next();
			if (pitem.getParentId() == root.getObject().getId()) {
				root.add(toNode(pitem));
				p.remove();
			}
		}

		log.debug("toTree: list.size before children calculation=" + list.size());
		if (!root.isRoot()) throw new IllegalStateException("!root.isRoot()");

		while (list.size() > 0) {
			log.debug("toTree: list.size=" + list.size());
			p = list.iterator();
			tree:
			while (p.hasNext()) {
				pitem = p.next();

				//hakkame puu seest parentit otsima
				//teame, et ruut ei saa parent olla, võib-olla on mõni lastest
				log.debug("root leaves=" + root.getLeaves().size());
				Iterator<TreeNode> q = root.childrenIterator();
				Iterator<TreeNode> prev = null;
				TreeNode<T> qitem;
				while (q.hasNext()) {
					qitem = q.next();

					int qid = qitem.getObject().getId();
					log.debug("qid=" + qid + ", pitem.id=" + pitem.getId() + ", pitem.parentId=" + pitem.getParentId());
					if (qid == pitem.getParentId()) {
						log.debug("leidsin parenti");

						TreeNode<T> qchild = toNode(pitem);
						qitem.add(qchild);

						if (qchild.getParent() != qitem) throw new IllegalStateException("qchild.getParent() != qitem");
						if (!qitem.isNodeChild(qchild)) throw new IllegalStateException("!qitem.isNodeChild(qchild)");

						p.remove();
						log.debug("qitem=" + qitem + ", qitem.children=" + qitem.getLeaves());

						break tree;
					}

					if (qitem.hasChildren()) {
						log.debug("qitem.hasChildren: ühe võrra sügavamale");
						prev = q;
						q = qitem.childrenIterator();
					} else if (prev != null && !q.hasNext()) {
						log.debug("tagasi ühe võtta ülespoole");
						q = prev;
					}
				}
			}

			log.debug("toTree: tree=" + toString(root.preorderIterator()));
		}

		return root;
	}

	public static String toString(Iterator<TreeNode> itr) {
		String str = "";
		while (itr.hasNext()) {
			str += (itr.next().getObject());
		}
		return str;
	}
}